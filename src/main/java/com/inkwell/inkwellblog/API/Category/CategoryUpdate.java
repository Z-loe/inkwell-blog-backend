package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.UpdateCategoryParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("category")
public class CategoryUpdate {
    @PostMapping("update")
    public BaseReturnData categoryUpdate(@RequestBody UpdateCategoryParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {

        // token鉴权
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

        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        BaseReturnData baseReturnData = new BaseReturnData();
        String getInfoSql = "select id from Category";
        sqliteHelper.executeQuery(getInfoSql, resultSet -> {
            baseReturnData.setCode(-1);
            baseReturnData.setMessage("更新失败");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                //更新
                if(Objects.equals(id, param.getId())){
                    String updateSql = "update Category set name ='%s' where id = '%s'".formatted(param.getName(),id);
                    try {
                        sqliteHelper.executeUpdate(updateSql);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    baseReturnData.setMessage("更新成功");
                    baseReturnData.setCode(200);
                    break;
                }
            }
            return null;
        });
        return baseReturnData;
    }
}
