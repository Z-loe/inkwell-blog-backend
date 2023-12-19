package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.Article.AddArticleParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.IDGenerator;
import org.springframework.web.bind.annotation.*;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;

import java.sql.SQLException;


@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleAdd
{
    /**
     * @description: 添加文章
     * @param: [param, token]
     * @return: com.inkwell.inkwellblog.ReturnData.BaseReturnData
     **/
    @PostMapping("add")
    public BaseReturnData articleAdd(@RequestBody AddArticleParam  param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException
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

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);

        String id = IDGenerator.generateID(8);
        String title = param.getTitle();
        String categoryId = param.getCategoryId();
        String content = param.getContent();
        String createTime = String.valueOf(System.currentTimeMillis());

        //插入数据到表
        String sql = "INSERT INTO Article (id,title,content,categoryId,createTime, comment) VALUES ('%s','%s','%s','%s','%s','%s')".formatted(id, title,content,categoryId,createTime,"[]");
        sqliteHelper.executeUpdate(sql);

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("添加成功");
        sqliteHelper.destroyed();
        return returnData;
//
    }

}
