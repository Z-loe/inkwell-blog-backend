package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.DetailArticleParam;
import com.inkwell.inkwellblog.ReturnData.ArticleDetailData;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.DetailData;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleDetail {
    @GetMapping("detail")
    public BaseReturnData articleDetail(@RequestBody DetailArticleParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {

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

        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        ArticleDetailData articleDetailData=new ArticleDetailData();
        DetailData detailData= new DetailData();
        String getInfoSql = "select * from Article where id = '%s'".formatted(param.getId());
        sqliteHelper.executeQuery(getInfoSql, resultSet -> {
            articleDetailData.setCode(-1);
            articleDetailData.setMessage("获取失败");
            if (resultSet.next()) {

                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String categoryId = resultSet.getString("categoryId");
                String content = resultSet.getString("content");
                String createTime = resultSet.getString("createTime");
                detailData.setId(id);
                detailData.setTitle(title);
                detailData.setCategoryId(categoryId);
                detailData.setContent(content);
                detailData.setCreateTime(createTime);
                articleDetailData.setData(detailData);
                articleDetailData.setCode(200);
                articleDetailData.setMessage("获取成功");
            }
            return null;
        });
        return articleDetailData;
    }
}
