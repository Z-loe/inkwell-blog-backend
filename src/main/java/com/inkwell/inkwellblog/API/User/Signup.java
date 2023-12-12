package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.RequestParam.SignupParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.IDGenerator;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@CrossOrigin
@RequestMapping("user")
public class Signup {
    /**
     * Signup请求
     * @return 返回状态码code和提示信息Message
     */
    @PostMapping("signup")
    public BaseReturnData signup(@RequestBody SignupParam param) throws SQLException, ClassNotFoundException {
        String account = param.getAccount();
        String password = param.getPassword();
        String nickname = param.getNickname();

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);

        //检查用户是否存在
        String sqlQueryString = "select count(*) from User where account = '%s'".formatted(account);
        int result = sqliteHelper.executeQuery(sqlQueryString, resultSet -> resultSet.getInt("count(*)"));
        UserData userData = new UserData();

        if(result!=0){
            userData.setCode(-1);
            userData.setMessage("账号已被注册");
            return userData;
        }

        //设置用户数据
        userData.setCode(200);
        userData.setMessage("注册成功");
        String uid = IDGenerator.generateID(10);
        userData.setUid(uid);
        userData.setAccount(account);
        userData.setUserType(0);
        userData.setToken("");
        userData.setNickname(nickname);

        //添入数据库
        int userType= userData.getUserType();
        String token= userData.getToken();

        String sqlInsertString ="insert into User VALUES('%s','%s','%s','%s',%d,'%s', '%s')".formatted(uid,nickname,account,password,userType,token, "[]");
        sqliteHelper.executeUpdate(sqlInsertString);

        return userData;
    }
}
