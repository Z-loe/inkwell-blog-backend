package com.inkwell.inkwellblog.API.Upload;

import com.inkwell.inkwellblog.ReturnData.ImageData;
import com.inkwell.inkwellblog.ReturnData.WangEditorResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("upload")
public class UploadImage {

    private final ResourceLoader resourceLoader;

    public UploadImage(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    /**
     * @description: 上传图片
     * @param: [files, map, request]
     * @return: java.lang.Object
     **/
    @PostMapping("/image")
    public Object uploadImage(@RequestParam("file") MultipartFile[] files, Map<String, Object> map, HttpServletRequest request) throws IOException {
        if (files == null || files.length == 0){
            WangEditorResponseData responseData = new  WangEditorResponseData();
            responseData.setErrno(1);
            responseData.setMessage("上传失败");
            return responseData;
        }
        String value;
        List<ImageData> data = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + file.getOriginalFilename();
            System.out.println(getFilePath(fileName));
            try (FileOutputStream fos = new FileOutputStream(getFilePath(fileName))) {
                fos.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            value = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/static/images/" + fileName;
            map.put("imgName", fileName);
            map.put("imgUrl", value);
            System.out.println(map);
            ImageData imageData = new ImageData();
            imageData.setUrl(value);
            data.add(imageData);
        }
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
        File filePath = new File(baseResourcePath + File.separator + "static/images", fileName);
        filePath.getParentFile().mkdirs();
        filePath.createNewFile();

        return filePath;
    }

}