package com.UserModel.UserModel.columns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
@CrossOrigin(origins = "*")
public class DBColumnController {
    @Autowired
    private DBService dbService;
    @Autowired
    private DBRespository dbRespository;

    @PostMapping("/create-column")
    public ResponseEntity<?> createNewColumn(@RequestBody DBColum columns) {
        Map<String, Object> responseObject = new HashMap<>();

        if (columns == null ||
                columns.getTable() == null ||
                columns.getName() == null ||
                columns.getType() == null
        ) {
            responseObject.put("response", "Invalid columns credentials provided");
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }

        String sql = "ALTER TABLE " +
                columns.getTable() + " ADD " +
                columns.getName() + " " +
                columns.getType();

        try {
            dbRespository.createDatabaseColumn(sql);
            responseObject.put("response", "Database column created successfully");

            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list-columns")
    public ResponseEntity<?> listAllColumns() {
        try {
            List<Map<String, Object>> users = dbService.getAllColumnsWithTypes("users");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/remove-column")
    public ResponseEntity<?> removeColumn(@RequestBody DBColum columns) {
        if (columns == null || columns.getTable() == null || columns.getName() == null) {
            return new ResponseEntity<>("Fields Missing!!", HttpStatus.BAD_REQUEST);
        }

        String sql = "ALTER TABLE " + columns.getTable() + " DROP COLUMN " + columns.getName();

        try {
            dbRespository.deleteDatabaseColumn(sql);
            return new ResponseEntity<>("Database column removed successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}