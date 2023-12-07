package com.inkwell.inkwellblog.Util;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;

import java.sql.SQLException;

public class TokenAuthenticate {
    /**
     * token鉴权
     * @param token 用户token
     * @return 鉴权结果 -1为未登录，0为无管理权限，1为管理权限
     */
    public static int checkToken(String token) throws SQLException, ClassNotFoundException {
        if (token.equals("")){
            return -1;
        }
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        String sql = "select * from User where token = '%s'".formatted(token);
        return sqliteHelper.executeQuery(sql, resultSet ->{
            // 如果没找到也是返回0
            // 先判断结果是否为null
            if (resultSet.next()) {
                int userType = resultSet.getInt("userType");
                if (userType == 1) {
                    return 1;
                } else if (userType == 0) {
                    return 0;
                }
            }
            return -1;
        });
    }
}
