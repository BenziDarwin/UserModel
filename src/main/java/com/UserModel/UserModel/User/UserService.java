package com.UserModel.UserModel.User;

import com.UserModel.UserModel.MailingService.MailingDetails;
import com.UserModel.UserModel.MailingService.MailingServiceService;
import com.UserModel.UserModel.ResetTokens.Models.TokenModel;
import com.UserModel.UserModel.ResetTokens.ResetTokens;
import com.UserModel.UserModel.ResetTokens.ResetTokensRepository;
import com.UserModel.UserModel.Roles.Roles;
import com.UserModel.UserModel.Roles.RolesRepository;
import com.UserModel.UserModel.Token.Token;
import com.UserModel.UserModel.Token.TokenRepository;
import com.UserModel.UserModel.Token.TokenType;
import com.UserModel.UserModel.Config.JwtService;
import com.UserModel.UserModel.User.Models.LoginRequest;
import com.UserModel.UserModel.User.Models.Passwords;
import com.UserModel.UserModel.User.Models.RegisterRequest;
import com.UserModel.UserModel.User.Models.UpdateUser;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final MailingServiceService mailingService;
    private final ResetTokensRepository resetRepository;
    private final RolesRepository rolesRepository;

    public AuthenticationResponse registerAdmin(RegisterRequest request) throws AuthException {
        var findUser = userRepository.findUserByEmail(request.getEmail());
        if (findUser.isPresent()) {
            throw new AuthException("User email already exists!");
        } else {
            Roles rl = rolesRepository.findRolesByName(request.getRole().toUpperCase()).orElseThrow();
            var user = User
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .persona(Persona.ADMIN)
                    .profileImage(request.getProfileImage())
                    .role(rl)
                    .password(passwordEncoder.encode(request.getPassword())).build();
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();
        }
    }

    public AuthenticationResponse registerUser(RegisterRequest request) throws AuthException {
        var findUser = userRepository.findUserByEmail(request.getEmail());
        if (findUser.isPresent()) {
            throw new AuthException("User email already exists!");
        } else {
            Roles rl = rolesRepository.findRolesByName(request.getRole().toUpperCase()).orElseThrow();
            var user = User
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .persona(Persona.USER)
                    .profileImage(request.getProfileImage())
                    .role(rl)
                    .password(passwordEncoder.encode(request.getPassword())).build();
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();
        }
    }

    public AuthenticationResponse login(LoginRequest request) throws AuthException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var checkPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!checkPassword) {
            throw new AuthException("Password or Email is incorrect!");
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public HashMap<String, String> addProperties(HashMap<String, String> properties) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        user.setProperties(properties);
        HashMap<String, String> res = new HashMap<>();
        res.put("result", "success");
        return res;
    }

    @Transactional
    public HashMap<String, Object> verify() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        HashMap<String, Object> res = new HashMap<>();
        res.put("email", user.getEmail());
        res.put("role", user.getRole().getRoleName());
        res.put("persona", user.getPersona().name());
        res.put("permissions", user.getRole().getPermissions());
        return res;
    }

    public HashMap<String, String> getUserByEmail(String email) {
        User val = userRepository.findUserByEmail(email).orElseThrow();
        HashMap<String, String> res = new HashMap<>();
        res.put("firstName", val.getFirstname());
        res.put("lastName", val.getLastname());
        return res;
    }

    @Transactional
    public HashMap<String, String> removeProperties(ArrayList<String> properties) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        Map<String, String> prt = deleteKeys(user.getProperties(), properties);
        user.setNewProperties(prt);
        HashMap<String, String> res = new HashMap<>();
        res.put("result", "success");
        return res;
    }

    @Transactional
    public HashMap<String, String> editInfo(UpdateUser updateUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        user.setProperties(updateUser.getProperties());
        user.setEmail(updateUser.getEmail());
        user.setFirstname(updateUser.getFirstname());
        user.setLastname(updateUser.getLastname());
        user.setProfileImage(updateUser.getProfileImage());
        HashMap<String, String> res = new HashMap<>();
        try {
            if (user.getRole().getRoleName().equals("ADMIN") || user.getPersona().equals(Persona.ADMIN)) {
                Roles role = rolesRepository.findRolesByName(updateUser.getRole().toUpperCase()).orElseThrow();
                user.setRole(role);
            } else {
                throw new AuthException("Insufficient permissions!");
            }
        } catch (Exception e) {
            res.put("error", e.getMessage());
        }
        res.put("result", "success");
        return res;
    }

    @Transactional
    public HashMap<String, String> changePassword(Passwords passwords) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        var checkPassword = passwordEncoder.matches(passwords.getOldPassword(), user.getPassword());
        if (checkPassword) {
            user.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
            user.setProfileImage(user.getProfileImage());
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } else {
            throw new AuthException("Old password is incorrect!");
        }

    }

    @Transactional
    public HashMap<String, String> resetPassword(String email) {
        var user = userRepository.findUserByEmail(email).orElseThrow();
        var password = RandomStringUtils.randomAlphanumeric(8);
        MailingDetails details = MailingDetails
                .builder()
                .recipient(new String[]{user.getEmail()})
                .msgBody("Default password: " + password).subject("One Time Password").build();
        mailingService.sendMail(details, "bob.wabusa@coseke.com");
        ResetTokens tokens = ResetTokens.builder().token(password).email(email).build();
        resetRepository.save(tokens);
        HashMap<String, String> res = new HashMap<>();
        res.put("result", "success");
        return res;
    }

    @Transactional
    public AuthenticationResponse validateToken(TokenModel model) throws AuthException {
        ResetTokens tokens = resetRepository.findResetTokensWithTokenAndEmail(model.getToken(), model.getEmail()).orElseThrow();
        var user = userRepository.findUserByEmail(tokens.getEmail()).orElseThrow(() -> new AuthException("User doesn't exist!"));
        if (!model.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(model.getNewPassword()));
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        resetRepository.delete(tokens);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public HashMap<String, String> emailTwoFactorAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        var password = RandomStringUtils.randomAlphanumeric(8);
        MailingDetails details = MailingDetails
                .builder()
                .recipient(new String[]{user.getEmail()})
                .msgBody("Default password: " + password).subject("One Time Password").build();
        mailingService.sendMail(details, "bob.wabusa@coseke.com");
        ResetTokens tokens = ResetTokens.builder().token(password).email(user.getEmail()).build();
        resetRepository.save(tokens);
        HashMap<String, String> res = new HashMap<>();
        res.put("result", "success");
        return res;
    }

    private Map<String, String> deleteKeys(Map<String, String> properties, ArrayList<String> array) {
        for (int index = 0; index < array.size(); index++) {
            properties.remove(array.get(index));
        }
        return properties;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
