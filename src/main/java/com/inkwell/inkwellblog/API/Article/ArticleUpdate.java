package com.inkwell.inkwellblog.API.Article;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.Article.UpdateArticleParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import com.inkwell.inkwellblog.Util.Constants;
import com.inkwell.inkwellblog.Util.TokenAuthenticate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleUpdate {
    /**
     * @description: 更新文章
     * @param: [param, token]
     * @return: com.inkwell.inkwellblog.ReturnData.BaseReturnData
     **/
    @PostMapping("update")
    public BaseReturnData articleUpdate(@RequestBody UpdateArticleParam param, @RequestHeader("token") String token) throws SQLException, ClassNotFoundException {

        //token鉴权
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
        BaseReturnData baseReturnData = new BaseReturnData();
        String getInfoSql = "select id from Article";
        sqliteHelper.executeQuery(getInfoSql, resultSet -> {
            baseReturnData.setCode(-1);
            baseReturnData.setMessage("更新失败");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                //更新
                if(Objects.equals(id, param.getId())){
                    String updateTime = String.valueOf(System.currentTimeMillis());
                    String updateSql = "update Article set title ='%s', content = '%s', categoryId = '%s', createTime = '%s' where id = '%s'".formatted(param.getTitle(),param.getContent(),param.getCategoryId(),updateTime,id);
                    try {
                        sqliteHelper.executeUpdate(updateSql);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    baseReturnData.setMessage("更新成功");
                    baseReturnData.setCode(200);
                    break;
                }
            }
            return null;
        });
        sqliteHelper.destroyed();
        return baseReturnData;
    }
}
