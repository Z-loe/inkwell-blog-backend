package com.inkwell.inkwellblog.API;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.RequestParam.LoginParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserData;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Login {

    @PostMapping("login")
    public BaseReturnData logIn(@RequestBody LoginParam param) throws SQLException, ClassNotFoundException
    {
        String account = param.getAccount();
        String password = param.getPassword();
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        String sql = "select count(*) from User where account = '%s'".formatted(account);
        int result = sqliteHelper.executeQuery(sql, resultSet ->
        {
            return resultSet.getInt("count(*)");
        });
       // System.out.println(result);

        if(result == 0 )
        {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(-1);
            returnData.setMessage("用户不存在");
            return returnData;
        }
        else
        {

            UserData userData = new UserData();

            String sqlUID      = "select UID from User where '%s'".formatted(account);
            String  UID = sqliteHelper.executeQuery(sqlUID, resultSet ->
            {
                return resultSet.getString("UID");
            });


            String sqlnickname = "select nickname from User where '%s'".formatted(account);
            String  nickname = sqliteHelper.executeQuery(sqlnickname, resultSet ->
            {
                return resultSet.getString("nickname");
            });

            String sqluserType = "select userType from User where '%s'".formatted(account);
            int  userType = sqliteHelper.executeQuery(sqluserType, resultSet ->
            {
                return resultSet.getInt("usertype");
            });

            String sqltoken    = "select token from User where '%s'".formatted(account);
            String  token = sqliteHelper.executeQuery(sqltoken, resultSet ->
            {
                return resultSet.getString("token");
            });
            userData.setCode(1);
            userData.setMessage("登录成功");
            userData.setUid(Integer.parseInt(UID));
            userData.setNickname(nickname);
            userData.setUserType(userType);
            userData.setToken(token);
            return userData;

        }

    }
}


