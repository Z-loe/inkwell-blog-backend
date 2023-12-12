package com.inkwell.inkwellblog.API.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.AddArticleParam;
import com.inkwell.inkwellblog.RequestParam.DeleteArticleParam;
import com.inkwell.inkwellblog.RequestParam.PostCommentParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentPost {
    @PostMapping("post")
    public BaseReturnData post(@RequestBody PostCommentParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException{

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
        String commentString="{ \"id\": "+id+",\"uid\": "+uid+",\"content\": "+commentParam+" }";

        String sqlQuery = "SELECT comment FROM Article WHERE id= "+id;
        sqliteHelper.executeQuery(sqlQuery, resultSet -> {
            String comment = resultSet.getString("comment");
            String commentList;
            if (comment == null || comment.equals("") || comment.equals("[]")) {
                commentList ="["+commentString+"]";
            }
            else {
                commentList= comment.substring(0, comment.length() - 1) + ","+commentString+"]";
            }

            //插入数据到表
            String sqlUpdate = "update Article set comment='" + commentList +  "' where id= "+id;
            try {
                sqliteHelper.executeUpdate(sqlUpdate);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("发表成功");
        sqliteHelper.destroyed();
        return returnData;
    }
}

