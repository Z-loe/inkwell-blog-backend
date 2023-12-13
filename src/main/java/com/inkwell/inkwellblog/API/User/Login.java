package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.RequestParam.User.LoginParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.UserData;
import com.inkwell.inkwellblog.Util.Constants;
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
        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
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

            String getInfoSql = "select uid, nickname, userType, avatar from User where account = '%s'".formatted(account);

            sqliteHelper.executeQuery(getInfoSql, resultSet -> {
                if (resultSet.next()) {
                    String uid = resultSet.getString("uid");
                    String nickname = resultSet.getString("nickname");
                    int userType = resultSet.getInt("userType");
                    String avatar = resultSet.getString("nickname");

                    // 将获取的数据设置到userData对象中
                    userData.setCode(200);
                    userData.setMessage("登录成功");
                    userData.setUid(uid);
                    userData.setNickname(nickname);
                    userData.setUserType(userType);
                    userData.setAccount(account);
                    userData.setAvatar(avatar);
                    // 利用UUID生成随机token
                    String token = UUID.randomUUID().toString();
                    userData.setToken(token);
                    // 保存token进数据库
                    String saveTokenSql = "update User set token = '%s' where uid = '%s'".formatted(token, uid);
                    try {
                        sqliteHelper.executeUpdate(saveTokenSql);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
                return null;
            });
            sqliteHelper.destroyed();
            return userData;

        }

    }
}


