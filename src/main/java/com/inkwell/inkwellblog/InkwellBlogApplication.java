package com.inkwell.inkwellblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class InkwellBlogApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(InkwellBlogApplication.class, args);
        new InitSqlite();
    }
}
