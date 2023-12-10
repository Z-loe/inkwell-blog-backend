package com.inkwell.inkwellblog.DataBase;

import java.sql.SQLException;

public class InitSqlite {

    public InitSqlite() throws SQLException, ClassNotFoundException {
        System.out.println("数据表检查...");
        SqliteHelper sqliteHelper = new SqliteHelper("inkwellDb.db");
        String sqlCreateUserTable = "create table if not exists User(uid text, nickname text, account text, password text, userType int, token text)";
        String sqlCreateCategoryTable = "create table if not exists Category(id text, name text)";
        String sqlCreateArticleTable = "create table if not exists Article(id text, title text, content text, categoryId text, createTime text)";
        sqliteHelper.executeUpdate(sqlCreateUserTable, sqlCreateArticleTable, sqlCreateCategoryTable);
        sqliteHelper.destroyed();
        System.out.println("数据表检查完成");
    }

//   // static public SqliteHelper getSqliteHelper(){
//        return sqliteHelper;
//    }

}
