package com.inkwell.inkwellblog.API.Comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.Comment.DeleteCommentParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentDelete
{
    /**
     * @description: 删除评论
     * @param: [param, token]
     * @return: com.inkwell.inkwellblog.ReturnData.BaseReturnData
     **/
    @PostMapping("delete")
    public BaseReturnData commentDelete(@RequestBody DeleteCommentParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException, JsonProcessingException {
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

        String deleteId = param.getId();
        String deleteCid= param.getCid();

        //转换为comment列，字符转化为数组
        String sqlQuery = "SELECT comment FROM Article WHERE id= "+deleteId;
        String comment =  sqliteHelper.executeQuery(sqlQuery, resultSet -> resultSet.getString("comment"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> resultList;
        try {
            resultList = objectMapper.readValue(comment, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // System.out.println(resultList);

        //根据cid查找、删除评论
       for(int i = 0; i < resultList.size(); i++)
       {
           String cid = (String) resultList.get(i).get("cid");
           if (Objects.equals(deleteCid, cid))
           {
               resultList.remove(i);
               break;
           }
       }
        // System.out.println(resultList);

        //转换为字符串
        String resultString = objectMapper.writeValueAsString(resultList);

        //插入数据到表
        String sqlDelete = "update Article set comment='" + resultString +  "' where id= "+deleteId;
        try {
            sqliteHelper.executeUpdate(sqlDelete);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("删除成功");

        sqliteHelper.destroyed();
        return returnData;
    }

}
