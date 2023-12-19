package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.User.PasswordParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("user")
public class SetPassword {
    /**
     * @description: 设置密码
     * @param: [param, token]
     * @return: com.inkwell.inkwellblog.ReturnData.BaseReturnData
     **/
    @PostMapping("set_password")
    public BaseReturnData password(@RequestBody PasswordParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {
        //token鉴权
        int checkResult = TokenAuthenticate.checkToken(token);
        if (checkResult == -1){
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("请先登录");
            return returnData;
        }

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        BaseReturnData baseReturnData=new BaseReturnData();

        //检查旧密码
        String sqlQuery="select password from User where uid ='%s'".formatted(param.getUid());
        String oldPassword = sqliteHelper.executeQuery(sqlQuery, resultSet -> resultSet.getString("password"));
        if(!Objects.equals(oldPassword, param.getOldPassword())){
            baseReturnData.setCode(-1);
            baseReturnData.setMessage("密码输入错误");
        }
        else{
            String sql = "update User set password='%s' where uid = '%s'".formatted(param.getNewPassword(),param.getUid());
            sqliteHelper.executeUpdate(sql);
            baseReturnData.setCode(200);
            baseReturnData.setMessage("修改成功");
        }

        sqliteHelper.destroyed();
        return baseReturnData;
    }
}
