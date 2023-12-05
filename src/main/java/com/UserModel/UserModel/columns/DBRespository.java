package com.UserModel.UserModel.columns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DBRespository {
    void createDatabaseColumn(String sql);
}
