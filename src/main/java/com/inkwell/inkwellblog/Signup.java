package com.inkwell.inkwellblog;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Signup {
    /**
     * Signup请求示例
     * @param userId uid参数
     * @param password password参数
     * @return 返回状态码code和提示信息Message
     */
    @PostMapping("signup")
    public UserData signup(@RequestParam String userId, @RequestParam String password, @RequestParam String nickname) throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper=InitSqlite.getSqliteHelper();
        //检查用户是否存在
        String sqlQueryString = "select * from User where userId = 123456";
        String result = sqliteHelper.executeQuery(sqlQueryString, resultSet -> {
            return resultSet.getString("password");
        });
        //添加数据
        String sqlAddString = "insert into User(userId, password) values('123456', 'abcde')";
        sqliteHelper.executeUpdate(sqlAddString);

        // UserData为数据类，用于封装数据
        UserData userData = new UserData();
        // 将传入的参数设置给数据类
        userData.setUserId("123456");
        userData.setPassword(result);
        // 返回数据 like:
        /*
         *{userId: uid, password: psw}
         * */
        return userData;
    }
}
