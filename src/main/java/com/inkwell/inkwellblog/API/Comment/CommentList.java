package com.inkwell.inkwellblog.API.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.Util.Constants;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentList {
    /**
     * @description: 查看评论
     * @param: [id]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @GetMapping("list")
    public Map<String, Object> commentList(@RequestParam String id) throws SQLException, ClassNotFoundException{

        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        String sqlQuery = "SELECT comment FROM Article WHERE id= "+id;
        String comment =sqliteHelper.executeQuery(sqlQuery, resultSet -> resultSet.getString("comment"));

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> commentList;
        try {
            commentList = mapper.readValue(comment, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Collections.reverse(commentList); // 排序
        // 构建返回结果
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", commentList);
        sqliteHelper.destroyed();
        return response;
    }
}
