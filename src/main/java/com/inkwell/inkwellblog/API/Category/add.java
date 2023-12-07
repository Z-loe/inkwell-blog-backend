package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import org.springframework.web.bind.annotation.*;
import com.inkwell.inkwellblog.RequestParam.AddParam;
import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("category")
public class add
{
    @PostMapping("add")
    public BaseReturnData adD(@RequestBody AddParam param) throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        String name = param.getName();

        //查找是否有同名文章类
        String sqlname = "select count(*) from User where name = '%s' ".formatted(name);
        int nameresult = sqliteHelper.executeQuery(sqlname, resultSet ->
        {
            return resultSet.getInt("count(*)");
        });
        if (nameresult == 0) {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(-1);
            returnData.setMessage("已存在同名文章类");
            return returnData;
        }

        //创建文章类
        else {
            //查找最大ID
            String sqlcountid = "select count(*) FROM Category";
            int countid = sqliteHelper.executeQuery(sqlcountid, resultSet ->
            {
                return resultSet.getInt("count(*)");
            });
            countid++;
            //插入文章名到表
            String sqlcreatename = "INSERT INTO Category (id,name) VALUES ('%s','%s')".formatted(countid, name);

            //返回状态码
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(200);
            returnData.setMessage("插入成功");
            return returnData;
        }

    }

}











