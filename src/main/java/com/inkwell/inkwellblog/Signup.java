package com.inkwell.inkwellblog;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Signup {
    /**
     * Signup请求示例
     * @param account uid参数
     * @param password password参数
     * @return 返回状态码code和提示信息Message
     */
    @PostMapping("signup")
    public ReturnData signup(@RequestParam String account, @RequestParam String password, @RequestParam String nickname) throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper=InitSqlite.getSqliteHelper();
        //检查用户是否存在
        String sqlQueryString = "select count(*) from User where account = '%s'".formatted(account);
        int result = sqliteHelper.executeQuery(sqlQueryString, resultSet -> {
            return resultSet.getInt("count(*)");
        });
        UserData userData = new UserData();

        if(result!=0){
            userData.setCode(0);
            userData.setMessage("用户名已被占用");
            return userData;
        }

        //设置用户数据
        userData.setCode(1);
        userData.setMessage("注册成功");
        String sqlQueryCountString = "select count(*) from User";
        int resultCount = sqliteHelper.executeQuery(sqlQueryCountString, resultSet -> {
            return resultSet.getInt("count(*)");
        });
        userData.setUid(resultCount+1);
        userData.setAccount(account);
        userData.setUserType(0);
        userData.setToken("hjghjk");
        userData.setNickname(nickname);

        //添入数据库
        int UID=userData.getUid();
        int userType= userData.getUserType();
        String token= userData.getToken();
        String sqlInsertString ="insert into User VALUES(%d,'%s','%s','%s',%d,'%s')".formatted(UID,nickname,account,password,userType,token);
        sqliteHelper.executeUpdate(sqlInsertString);
        return userData;
    }
}
