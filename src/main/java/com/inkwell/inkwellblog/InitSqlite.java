package com.inkwell.inkwellblog;

import java.sql.SQLException;

public class InitSqlite {
    private static SqliteHelper sqliteHelper = null;
    InitSqlite() throws SQLException, ClassNotFoundException {
        sqliteHelper = new SqliteHelper("inkwellDb.db");
        String sqlCreateString = "create table if not exists User(userId text, password text, nickname text, userType int)";
        sqliteHelper.executeUpdate(sqlCreateString);
    }

    static public SqliteHelper getSqliteHelper(){
        return sqliteHelper;
    }

}
