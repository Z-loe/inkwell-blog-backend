package com.inkwell.inkwellblog.API.Comment;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.AddArticleParam;
import com.inkwell.inkwellblog.RequestParam.PostCommentParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.IDGenerator;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.SQLException;

public class Post {
    @PostMapping("post")
    public BaseReturnData post(@RequestBody PostCommentParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException
    {
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

        String id = IDGenerator.generateID(8);
        String title = param.getTitle();
        String categoryId = param.getCategoryId();
        String content = param.getContent();
        String createTime = String.valueOf(System.currentTimeMillis());

        //插入数据到表
        String sql = "INSERT INTO Article (id,title,content,categoryId,createTime) VALUES ('%s','%s','%s','%s','%s')".formatted(id, title,content,categoryId,createTime);
        sqliteHelper.executeUpdate(sql);

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("添加成功");
        sqliteHelper.destroyed();
        return returnData;
//
    }

}
}
