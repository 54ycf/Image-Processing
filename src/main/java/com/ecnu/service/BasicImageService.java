package com.ecnu.service;

import com.ecnu.pojo.LineParam;
import com.ecnu.pojo.GeoParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BasicImageService {


    Map<String, String> GeoTransform(MultipartFile file, GeoParam param);

    Map<String, String> GeoTransformMulti(MultipartFile file, MultipartFile file2, GeoParam param);

    Map<String, String> LineDetection(MultipartFile file, LineParam param);

    Map<String, String> EdgeDetection(MultipartFile file, String operator);

    Map<String, String> Morphology(MultipartFile file, int len);

    Map<String, String> NoiseFilter(MultipartFile file, String filter);

}
