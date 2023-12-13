package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.IDGenerator;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;
import com.inkwell.inkwellblog.RequestParam.Category.AddCategoryParam;
import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("category")
public class CategoryAdd
{
    @PostMapping("add")
    public BaseReturnData categoryAdd(@RequestBody AddCategoryParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {

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

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        String name = param.getName();

        //查找是否有同名文章类
        String sqlName = "select count(*) from Category where name = '%s' ".formatted(name);
        int nameResult = sqliteHelper.executeQuery(sqlName, resultSet ->
        {
            return resultSet.getInt("count(*)");
        });
        if (nameResult != 0) {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(-1);
            returnData.setMessage("已存在同名分类");
            return returnData;
        }
        else {
            //创建文章类
            String id = IDGenerator.generateID(6);
            //插入文章名到表
            String sqlCreateName = "INSERT INTO Category (id,name) VALUES ('%s','%s')".formatted(id, name);
            sqliteHelper.executeUpdate(sqlCreateName);
            //返回状态码
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(200);
            returnData.setMessage("添加成功");
            sqliteHelper.destroyed();
            return returnData;
        }

    }

}











