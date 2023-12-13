package com.inkwell.inkwellblog.API.Upload;

import com.inkwell.inkwellblog.DataBase.SqliteHelper;
import com.inkwell.inkwellblog.ReturnData.ImageData;
import com.inkwell.inkwellblog.ReturnData.WangEditorResponseData;
import com.inkwell.inkwellblog.Util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("upload")
public class UploadUserAvatar {

    private final ResourceLoader resourceLoader;

    public UploadUserAvatar(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/avatar")
    public Object uploadUserAvatar(@RequestParam("file") MultipartFile[] files, @RequestParam("uid") String uid, Map<String, Object> map, HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException {
        if (files == null || files.length == 0){
            WangEditorResponseData responseData = new  WangEditorResponseData();
            responseData.setErrno(1);
            responseData.setMessage("未选择文件");
            return responseData;
        }
        String value = null;
        List<ImageData> data = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + file.getOriginalFilename();
            System.out.println(getFilePath(fileName));
            try (FileOutputStream fos = new FileOutputStream(getFilePath(fileName))) {
                fos.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            value = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/static/userAvatars/" + fileName;
            map.put("avatarName", fileName);
            map.put("avatarUrl", value);
            System.out.println(map);
            ImageData imageData = new ImageData();
            imageData.setUrl(value);
            data.add(imageData);
        }
        SqliteHelper sqliteHelper = new SqliteHelper(Constants.DATABASE_PATH);
        String sql = "update User set avatar='%s' where uid='%s'".formatted(value, uid);
        sqliteHelper.executeUpdate(sql, sql);
        WangEditorResponseData responseData = new  WangEditorResponseData();
        responseData.setErrno(0);
        responseData.setMessage("上传成功");
        responseData.setData(data);
        return responseData;
    }

    private File getFilePath(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/");
        File staticDir = resource.getFile();

        // 获取resources目录的路径
        String baseResourcePath = staticDir.getParentFile().getCanonicalPath();
        File filePath = new File(baseResourcePath + File.separator + "static/userAvatars", fileName);
        filePath.getParentFile().mkdirs();
        filePath.createNewFile();

        return filePath;
    }

}