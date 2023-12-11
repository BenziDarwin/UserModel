package com.UserModel.UserModel.columns;

import com.UserModel.UserModel.User.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DBService implements DBRespository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    public DBService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void createDatabaseColumn(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public void deleteDatabaseColumn(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Transactional
    public List<Map<String, Object>> getAllColumnsWithTypes(String tableName) {
        try {
            String sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?";
            return jdbcTemplate.queryForList(sql, tableName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
