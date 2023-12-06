package com.inkwell.inkwellblog.API;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.RequestParam.SignupParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserData;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

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
        String account = param.getAccount();
        String password = param.getPassword();
        String nickname = param.getNickname();

        SqliteHelper sqliteHelper= InitSqlite.getSqliteHelper();

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
//        String sqlQueryCountString = "select count(*) from User";
//        int resultCount = sqliteHelper.executeQuery(sqlQueryCountString, resultSet -> resultSet.getInt("count(*)"));
        // 根据随机UUID哈希值生成UID
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode <0){
            hashCode=-hashCode;
        }
        // 0 代表前面补充0
        // 10 代表长度为10
        // d 代表参数为正数型
        String uid = String.format("%010d", hashCode).substring(0,10);

        userData.setUid(uid);
        userData.setAccount(account);
        userData.setUserType(0);
        userData.setToken("");
        userData.setNickname(nickname);

        //添入数据库
        String UID=userData.getUid();
        int userType= userData.getUserType();
        String token= userData.getToken();

        String sqlInsertString ="insert into User VALUES('%s','%s','%s','%s',%d,'%s')".formatted(UID,nickname,account,password,userType,token);
        sqliteHelper.executeUpdate(sqlInsertString);

        return userData;
    }
}
