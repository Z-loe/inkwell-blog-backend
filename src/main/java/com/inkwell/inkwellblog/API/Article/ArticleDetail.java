package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.ArticleDetailData;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.DetailData;
import com.inkwell.inkwellblog.Util.Constants;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleDetail {
    @GetMapping("detail")
    public BaseReturnData articleDetail(@RequestParam("id") String id) throws SQLException, ClassNotFoundException {

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        ArticleDetailData articleDetailData=new ArticleDetailData();
        DetailData detailData= new DetailData();
        String getInfoSql = "select * from Article where id = '%s'".formatted(id);
        sqliteHelper.executeQuery(getInfoSql, resultSet -> {
            articleDetailData.setCode(-1);
            articleDetailData.setMessage("获取失败");
            if (resultSet.next()) {

                String rid = resultSet.getString("id");
                String title = resultSet.getString("title");
                String categoryId = resultSet.getString("categoryId");
                String content = resultSet.getString("content");
                String createTime = resultSet.getString("createTime");
                detailData.setId(rid);
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
        sqliteHelper.destroyed();
        return articleDetailData;
    }
}
