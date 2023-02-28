package com.ecnu.controller;

import com.ecnu.util.MyFileUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    MyFileUtil mfu;

    @GetMapping("/{type}/{filename}")
    public void getFile(@PathVariable String type, @PathVariable String filename, HttpServletResponse response) {
        String resourcePath = "/" + type  + '/' + filename;
        mfu.getFileStream(resourcePath, response);
    }

}
