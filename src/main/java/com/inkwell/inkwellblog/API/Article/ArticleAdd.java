package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.AddArticleParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.IDGenerator;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("article")

public class ArticleAdd
{
    @PostMapping("add")
    public BaseReturnData categoryAdd(@RequestBody AddArticleParam  param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException
    {
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

        String id = IDGenerator.generateID(10);
        String title = param.getTitle();
        String categoryId = param.getCategoryId();
        String content = param.getContent();
        String updateTime = String.valueOf(System.currentTimeMillis());

        //插入数据到表
        String sql = "INSERT INTO Article (id,title,categoryId,content,createTime) VALUES ('%s','%s'，'%s'，'%s'，'%s')".formatted(id, title,categoryId,content,updateTime);
        sqliteHelper.executeUpdate(sql);

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("添加成功");
        return returnData;
//
    }

}
