package com.inkwell.inkwellblog.API.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.Comment.PostCommentParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentPost {
    @PostMapping("post")
    public BaseReturnData post(@RequestBody PostCommentParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException, JsonProcessingException {

        // token鉴权
        int checkResult = TokenAuthenticate.checkToken(token);
        if (checkResult == -1){
            BaseReturnData returnData = new BaseReturnData();
            returnData.setCode(403);
            returnData.setMessage("请先登录");
            return returnData;
        }
        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        String id = param.getId();
        String uid = param.getUid();
        String commentParam = param.getComment();

        String sqlQuery = "SELECT comment FROM Article WHERE id= "+id;
        String comment =  sqliteHelper.executeQuery(sqlQuery, resultSet -> resultSet.getString("comment"));

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String, Object> commentData = new HashMap<>();
        commentData.put("id", id);
        commentData.put("uid", uid);
        commentData.put("content", commentParam);
        commentData.put("time", System.currentTimeMillis());

        List<HashMap<String, Object>> resultList;

        try {
            resultList = objectMapper.readValue(comment, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        resultList.add(commentData);
        //转换为字符串
        String resultString = objectMapper.writeValueAsString(resultList);

        //插入数据到表
        String sqlUpdate = "update Article set comment='" + resultString +  "' where id= "+id;
        try {
            sqliteHelper.executeUpdate(sqlUpdate);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("发表成功");
        sqliteHelper.destroyed();
        return returnData;
    }
}

