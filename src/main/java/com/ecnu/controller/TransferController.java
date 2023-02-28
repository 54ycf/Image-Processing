package com.ecnu.controller;

import com.ecnu.service.FastStyleTransferService;
import com.ecnu.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    FastStyleTransferService transferService;

    @RequestMapping("/img")
    public Result imgTransfer(@RequestParam("file") MultipartFile file, @RequestParam("style") String style) {
        Map<String, String> map = transferService.imgTransfer(file, style);
        return Result.success(map);
    }
}
