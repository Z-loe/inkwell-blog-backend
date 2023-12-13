package com.inkwell.inkwellblog.API.User;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.User.NicknameParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@RestController
@CrossOrigin
@RequestMapping("user")
public class Nickname {
    @PostMapping("nickname")
    public BaseReturnData nickname(@RequestBody NicknameParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {
        //token鉴权
        int checkResult = TokenAuthenticate.checkToken(token);
        if (checkResult == -1){
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("请先登录");
            return returnData;
        } else if(checkResult == 0){
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("您没有权限执行此操作");
            return returnData;
        }

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        String sql = "update User set nickname='%s' where uid = '%s'".formatted(param.getNickname(),param.getUid());
        sqliteHelper.executeUpdate(sql);

        BaseReturnData baseReturnData=new BaseReturnData();
        baseReturnData.setCode(200);
        baseReturnData.setMessage("修改成功");

        sqliteHelper.destroyed();
        return baseReturnData;
    }
}
