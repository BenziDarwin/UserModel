package com.UserModel.UserModel.columns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
@CrossOrigin(origins = "*")
public class DBColumnController {
    private DBService dbService;
    @Autowired
    private DBRespository dbRespository;

    @PostMapping("/create-column")
    public ResponseEntity<Map<String, Object>> createNewColumn(@RequestBody DBColum columns) {
        Map<String, Object> responseObject = new HashMap<>();

        if (columns == null || columns.getTable() == null || columns.getName() == null || columns.getType() == null) {
            responseObject.put("response", "Invalid columns credentials provided");
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }

        String sql = "ALTER TABLE " + columns.getTable() + " ADD " + columns.getName() + " " + columns.getType();

        try {
            dbRespository.createDatabaseColumn(sql);
            responseObject.put("response", "Database column created successfully");

            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.put("response", "Internal server error");
            return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
