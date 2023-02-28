package com.ecnu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FastStyleTransferService {
    Map<String,String> imgTransfer(MultipartFile file, String style);
}
