package com.ecnu.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.ecnu.config.EnvInfo;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class MyFileUtil {

    @Resource(name = "envInfo")
    private EnvInfo env;

    private static final List<String> imgTypes = Arrays.asList("jpg","png");

    public String saveTempImg(MultipartFile file){
        if (!isImg(file)) return null;
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (suffix.length() == 1){
            return null; //没有后缀去指定类型
        }
        String uuid = UUID.randomUUID().toString().replace("-","");

        String fileNewName = uuid + suffix;
        String savePath = env.TargetImagePath + "/tmp/" + fileNewName;
        try {
            FileUtil.writeBytes(file.getBytes(), savePath);
            return savePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    public void getFileStream(String resourcePath, HttpServletResponse response) {
        ServletOutputStream os;

        String targetPath = env.TargetImagePath + resourcePath;
        String fileName = resourcePath.substring(resourcePath.lastIndexOf('/')+1);
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        byte[] bytes = FileUtil.readBytes(targetPath);
        os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    @SneakyThrows
    private static boolean isImg(MultipartFile file){
        String type = FileTypeUtil.getType(file.getInputStream());
        return imgTypes.contains(type);
    }
}
