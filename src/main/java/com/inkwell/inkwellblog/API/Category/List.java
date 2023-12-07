package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.ArticleData;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.CategoryDate;
import com.inkwell.inkwellblog.ReturnData.UserData;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("category")
public class List {
    @GetMapping("list")
    public BaseReturnData list() throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        //检查类型是否为空
        String sqlCount = "select count(*) from Category";
        int resultCount = sqliteHelper.executeQuery(sqlCount, resultSet -> resultSet.getInt("count(*)"));
        ArticleData articleData = new ArticleData();

        if(resultCount==0){
            articleData.setCode(-1);
            articleData.setMessage("暂时还没有文章类型");
        }

        //查询
        articleData.setCode(200);
        articleData.setMessage("查询成功");

        String getInfoSql = "select id,name from Category";
            sqliteHelper.executeQuery(getInfoSql, resultSet -> {
                CategoryDate[] categoryDate =new CategoryDate[resultCount];
                int i=0;
                while (resultSet.next()) {

                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");

                    // 将获取的数据设置到articleData对象中
                    categoryDate[i] =new CategoryDate();
                    categoryDate[i].setId(id);
                    categoryDate[i].setName(name);
                    i++;
                }
                articleData.setRows(categoryDate);
                return null;
            });

        return articleData;
    }
}
