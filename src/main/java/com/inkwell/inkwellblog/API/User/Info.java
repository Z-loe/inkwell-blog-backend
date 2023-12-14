package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.Util.Constants;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("user")
public class Info {
    @GetMapping("info")
    public Map<String, Object> info(@RequestParam("uid") String uid) throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);

        String sqlQueryString = "select * from User where uid = '%s'".formatted(uid);
        Map<String, Object> response=sqliteHelper.executeQuery(sqlQueryString, resultSet -> {
            Map<String, Object> result = new HashMap<>();
            if (resultSet.next()) {
                String nickname = resultSet.getString("nickname");
                String uidString = resultSet.getString("uid");
                String account = resultSet.getString("account");
                int userType = resultSet.getInt("userType");
                String avatar = resultSet.getString("avatar");

                result.put("code", 200);
                result.put("message", "查询成功");
                result.put("data", new HashMap<String, Object>() {{
                    put("nickname", nickname);
                    put("uid", uidString);
                    put("account", account);
                    put("userType",userType);
                    put("avatar", avatar);
                }});
            }
            else{
                result.put("code", -1);
                result.put("message", "查询失败");
            }
            return result;
        });

        sqliteHelper.destroyed();
        return response;
    }
}
