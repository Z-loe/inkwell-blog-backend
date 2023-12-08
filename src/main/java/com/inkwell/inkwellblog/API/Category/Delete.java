package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.DeleteCategoryParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("category")
public class Delete {
    @PostMapping("delete")
    public BaseReturnData delete(@RequestBody DeleteCategoryParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {
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
            baseReturnData.setMessage("删除失败");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                //查找
                if(Objects.equals(id, param.getId())){
                    String Sql = "delete from Category where id='%s'".formatted(id);
                    try {
                        sqliteHelper.executeUpdate(Sql);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    baseReturnData.setMessage("删除成功");
                    baseReturnData.setCode(200);
                    break;
                }
            }
            return null;
        });
        return baseReturnData;
    }
}
