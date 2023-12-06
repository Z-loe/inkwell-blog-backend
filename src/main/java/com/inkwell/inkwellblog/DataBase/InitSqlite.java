package com.inkwell.inkwellblog.DataBase;

import java.sql.SQLException;

public class InitSqlite {
    private static SqliteHelper sqliteHelper = null;
    public InitSqlite() throws SQLException, ClassNotFoundException {
        sqliteHelper = new SqliteHelper("inkwellDb.db");
        String sqlCreateString = "create table if not exists User(UID text, nickname text, account text, password text, userType int, token text)";
        sqliteHelper.executeUpdate(sqlCreateString);
    }

    static public SqliteHelper getSqliteHelper(){
        return sqliteHelper;
    }

}
