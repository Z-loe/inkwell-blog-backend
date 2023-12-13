package com.inkwell.inkwellblog.API.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.PostCommentParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentList {
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

        // 构建返回结果
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data",commentList);
        sqliteHelper.destroyed();
        return response;
    }
}
