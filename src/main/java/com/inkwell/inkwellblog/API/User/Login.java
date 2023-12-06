package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.RequestParam.LoginParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserData;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("user")
public class Login {

    @PostMapping("login")
    public BaseReturnData logIn(@RequestBody LoginParam param) throws SQLException, ClassNotFoundException
    {
        String account = param.getAccount();
        String password = param.getPassword();
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        String sql = "select count(*) from User where account = '%s' and password = '%s'".formatted(account, password);
        int result = sqliteHelper.executeQuery(sql, resultSet ->
        {
            return resultSet.getInt("count(*)");
        });
       // System.out.println(result);

        if(result == 0 )
        {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(-1);
            returnData.setMessage("账号或密码错误");
            return returnData;
        } else
        {
            UserData userData = new UserData();

            String getInfoSql = "select UID, nickname, userType from User where account = '%s'".formatted(account);

            sqliteHelper.executeQuery(getInfoSql, resultSet -> {
                if (resultSet.next()) {
                    String UID = resultSet.getString("UID");
                    String nickname = resultSet.getString("nickname");
                    int userType = resultSet.getInt("userType");

                    // 将获取的数据设置到userData对象中
                    userData.setCode(200);
                    userData.setMessage("登录成功");
                    userData.setUid(UID);
                    userData.setNickname(nickname);
                    userData.setUserType(userType);
                    userData.setAccount(account);
                    // 利用UUID生成随机token
                    String token = UUID.randomUUID().toString();
                    userData.setToken(token);
                    // 保存token进数据库
                    String saveTokenSql = "update User set token = '%s' where UID = '%s'".formatted(token, UID);
                    try {
                        sqliteHelper.executeUpdate(saveTokenSql);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
                return null;
            });
            return userData;

//            UserData userData = new UserData();
//
//            String sqlUID      = "select UID from User where '%s'".formatted(account);
//            String  UID = sqliteHelper.executeQuery(sqlUID, resultSet ->
//            {
//                return resultSet.getString("UID");
//            });
//
//
//            String sqlnickname = "select nickname from User where '%s'".formatted(account);
//            String  nickname = sqliteHelper.executeQuery(sqlnickname, resultSet ->
//            {
//                return resultSet.getString("nickname");
//            });
//
//            String sqluserType = "select userType from User where '%s'".formatted(account);
//            int  userType = sqliteHelper.executeQuery(sqluserType, resultSet ->
//            {
//                return resultSet.getInt("usertype");
//            });
//
//            String sqltoken    = "select token from User where '%s'".formatted(account);
//            String  token = sqliteHelper.executeQuery(sqltoken, resultSet ->
//            {
//                return resultSet.getString("token");
//            });
//            userData.setCode(1);
//            userData.setMessage("登录成功");
//            userData.setUid(Integer.parseInt(UID));
//            userData.setNickname(nickname);
//            userData.setUserType(userType);
//            userData.setToken(token);
//            return userData;

        }

    }
}


