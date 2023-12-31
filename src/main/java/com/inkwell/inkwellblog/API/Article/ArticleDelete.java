package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.Article.DeleteArticleParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@RestController
@CrossOrigin
@RequestMapping("article")

public class ArticleDelete
{
    /**
     * @description: 删除文章
     * @param: [param, token]
     * @return: com.inkwell.inkwellblog.ReturnData.BaseReturnData
     **/
    @PostMapping("delete")
    public BaseReturnData categoryDelete(@RequestBody DeleteArticleParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException
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

        String deleteId = param.getId();

        String sql = "DELETE FROM Article  WHERE id = '%s' ".formatted(deleteId);
        sqliteHelper.executeUpdate(sql);

        //返回状态码
        BaseReturnData returnData = new BaseReturnData();
        returnData.setCode(200);
        returnData.setMessage("删除成功");

        sqliteHelper.destroyed();
        return returnData;

    }

}
