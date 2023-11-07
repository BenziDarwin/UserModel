package com.UserModel.UserModel.User;

import com.UserModel.UserModel.Token.Token;
import com.UserModel.UserModel.Token.TokenRepository;
import com.UserModel.UserModel.Token.TokenType;
import com.UserModel.UserModel.User.Config.JwtService;
import com.UserModel.UserModel.User.Models.LoginRequest;
import com.UserModel.UserModel.User.Models.Passwords;
import com.UserModel.UserModel.User.Models.RegisterRequest;
import com.UserModel.UserModel.User.Models.UpdateUser;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request) throws AuthException {
        var findUser = userRepository.findUserByEmail(request.getEmail());
        if(findUser.isPresent()) {
            throw new AuthException("User email already exists!");
        } else {
            var user = User
                    .builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .persona(Persona.USER)
                    .profileImage(request.getProfileImage())
                    .password(passwordEncoder.encode(request.getPassword())).build();
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse
                    .builder().token(jwtToken).refreshToken(refreshToken)
                    .build();
        }
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public HashMap<String,String> addProperties(HashMap<String,String> properties) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        user.setProperties(properties);
        HashMap<String, String> res = new HashMap<>();
        res.put("result","success");
        return res;
    }

    @Transactional
    public HashMap<String,String> removeProperties(ArrayList<String> properties) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        Map<String,String> prt = deleteKeys(user.getProperties(),properties);
        user.setNewProperties(prt);
        HashMap<String, String> res = new HashMap<>();
        res.put("result","success");
        return res;
    }

    @Transactional
    public HashMap<String, String> editInfo(UpdateUser updateUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        user.setProperties(updateUser.getProperties());
        user.setEmail(user.getEmail());
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setProfileImage(user.getProfileImage());
        HashMap<String, String> res = new HashMap<>();
        res.put("result","success");
        return res;
    }

    @Transactional
    public HashMap<String, String> changePassword(Passwords passwords) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        var checkPassword = passwordEncoder.matches(passwords.getOldPassword(), user.getPassword());
        if(checkPassword) {
            user.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
            user.setProfileImage(user.getProfileImage());
            HashMap<String, String> res = new HashMap<>();
            res.put("result","success");
            return res;
        } else {
            throw new AuthException("Old password is incorrect!");
        }

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
