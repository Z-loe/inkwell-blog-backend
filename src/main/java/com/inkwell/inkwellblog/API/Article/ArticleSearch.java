package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.SearchArticleParam;
import com.inkwell.inkwellblog.ReturnData.ArticleSearchData;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.ReturnData.DetailData;
import com.inkwell.inkwellblog.ReturnData.SearchData;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleSearch {
    @GetMapping("search")
    public BaseReturnData articleSearch(@RequestBody SearchArticleParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {

        //token鉴权
        int checkResult = TokenAuthenticate.checkToken(token);
        if (checkResult == -1) {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("请先登录");
            return returnData;
        } else if (checkResult == 0) {
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("您没有权限执行此操作");
            return returnData;
        }

        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        ArticleSearchData articleSearchData = new ArticleSearchData();

        //默认值检测
        int page = param.getPage() == 0 ? 1 : param.getPage();
        int pageSize = param.getPageSize() == 0 ? 10 : param.getPageSize();
        String categoryId = param.getCategoryId() == null ? "" : param.getCategoryId();
        String keyword = param.getKeyword() == null ? "" : param.getKeyword();

        //构建sql语句
        String sqlSearch = "select * from Article ";
        boolean flag = true;
        if (!Objects.equals(keyword, "")) {
            sqlSearch += "where ";
            flag = false;
            sqlSearch += "(title like '%Keyword%' or content like '%Keyword%') ".replace("Keyword", param.getKeyword());
        }

        if (!Objects.equals(categoryId, "")) {
            if (flag) {
                sqlSearch += "where ";
            } else {
                sqlSearch += "and ";
            }
            sqlSearch += "categoryId ='%s' ".formatted(param.getCategoryId());
        }
        sqlSearch += "limit (%d*%d),%d".formatted(page-1, pageSize, pageSize);
        String sqlSearchCount="select count(*) from ( "+ sqlSearch+")";
        final int[] count = new int[1];

        //查询
        sqliteHelper.executeQuery(sqlSearchCount, resultSet -> {
            count[0] = resultSet.getInt("count(*)");
            if(count[0] ==0){
                articleSearchData.setCode(201);
                articleSearchData.setMessage("没有匹配的结果");
            }
            return null;
        });
        sqliteHelper.executeQuery(sqlSearch, resultSet -> {
            DetailData[] detailData = new DetailData[count[0]];
            int i = 0;
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String categoryID = resultSet.getString("categoryId");
                String createTime = resultSet.getString("createTime");

                // 将获取的数据设置到articleData对象中
                detailData[i] = new DetailData();
                detailData[i].setId(id);
                detailData[i].setTitle(title);
                detailData[i].setCategoryId(categoryID);
                detailData[i].setCreateTime(createTime);
                i++;
            }
            SearchData searchData =new SearchData();
            searchData.setKeyword(param.getKeyword());
            searchData.setCategoryId(param.getCategoryId());
            searchData.setPage(page);
            searchData.setPageSize(pageSize);
            searchData.setRows(detailData);
            articleSearchData.setCode(200);
            articleSearchData.setMessage("查询成功");
            articleSearchData.setData(searchData);
            return null;
        });
        return articleSearchData;
    }
}
