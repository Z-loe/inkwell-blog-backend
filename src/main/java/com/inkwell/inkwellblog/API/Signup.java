package com.inkwell.inkwellblog.API;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.RequestParam.SignupParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserDataBase;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Signup {
    /**
     * Signup请求
     * @return 返回状态码code和提示信息Message
     */
    @PostMapping("signup")
    public BaseReturnData signup(@RequestBody SignupParam param) throws SQLException, ClassNotFoundException {
        String  account = param.getAccount();
        String  password = param.getPassword();
        String nickname = param.getNickname();
        SqliteHelper sqliteHelper= InitSqlite.getSqliteHelper();
        //检查用户是否存在
        String sqlQueryString = "select count(*) from User where account = '%s'".formatted(account);
        int result = sqliteHelper.executeQuery(sqlQueryString, resultSet -> {
            return resultSet.getInt("count(*)");
        });
        UserDataBase userData = new UserDataBase();

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
