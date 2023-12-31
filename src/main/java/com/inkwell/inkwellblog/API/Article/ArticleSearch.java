package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.Util.Constants;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleSearch {
    /**
     * @description:文章搜索
     * @param: [keyword, categoryId, page, pageSize]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @GetMapping("search")
    public Map<String, Object> searchArticles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) throws SQLException, ClassNotFoundException {

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        try {
            // 构建 SQL 查询语句
            String sql = "SELECT id, title, substr(content, 0, 50) as content, categoryId, createTime FROM Article WHERE 1=1";
            if (keyword != null && !keyword.equals("")) {
                sql += " AND (title LIKE '%" + keyword + "%' OR content LIKE '%" + keyword + "%')";
            }
            if (categoryId != null && !categoryId.equals("")) {
                sql += " AND categoryId = '" + categoryId + "'";
            }
            sql += " ORDER BY CAST(createTime AS timestamp) DESC";
            sql += " LIMIT " + pageSize + " OFFSET " + ((page - 1) * pageSize);
            // 执行查询并处理结果
            List<Map<String, Object>> articleList = sqliteHelper.executeQuery(sql, rs -> {
                List<Map<String, Object>> result = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> article = new HashMap<>();
                    article.put("id", rs.getString("id"));
                    article.put("title", rs.getString("title"));
                    article.put("categoryId", rs.getString("categoryId"));
                    article.put("createTime", rs.getString("createTime"));
                    article.put("content", rs.getString("content"));
                    result.add(article);
                }
                return result;
            });
            // 优先输出最新文章
//            Collections.reverse(articleList);

            // 查询总数
            String countSql = "SELECT COUNT(*) AS count FROM Article WHERE 1=1";
            if (keyword != null && !keyword.equals("")) {
                countSql += " AND (title LIKE '%" + keyword + "%' OR content LIKE '%" + keyword + "%')";
            }
            if (categoryId != null && !categoryId.equals("")) {
                countSql += " AND categoryId = '" + categoryId + "'";
            }
            int count = sqliteHelper.executeQuery(countSql, rs -> rs.getInt("count"));

            // 构建返回结果
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", new HashMap<String, Object>() {{
                put("keyword", keyword);
                put("category", categoryId);
                put("page", page);
                put("pageSize", pageSize);
                put("rows",articleList);
                put("count", count);
            }});
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
            return response;
        }
    }
}
