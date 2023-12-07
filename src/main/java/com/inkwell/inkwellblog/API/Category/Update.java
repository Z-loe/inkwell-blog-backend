package com.inkwell.inkwellblog.API.Category;

import com.inkwell.inkwellblog.DataBase.InitSqlite;
import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.RequestParam.UpdateParam;
import com.inkwell.inkwellblog.ReturnData.BaseReturnData;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("category")
public class Update {
    @PostMapping("update")
    public BaseReturnData update(UpdateParam param) throws SQLException, ClassNotFoundException {
        SqliteHelper sqliteHelper = InitSqlite.getSqliteHelper();
        BaseReturnData baseReturnData = new BaseReturnData();
        String getInfoSql = "select id from Category";
        sqliteHelper.executeQuery(getInfoSql, resultSet -> {
            baseReturnData.setCode(-1);
            baseReturnData.setMessage("更新失败");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                //更新
                if(Objects.equals(id, param.getId())){
                    String updateSql = "update Category set name ='%s' where id = '%s'".formatted(param.getName(),param.getId());
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
        return baseReturnData;
    }
}
