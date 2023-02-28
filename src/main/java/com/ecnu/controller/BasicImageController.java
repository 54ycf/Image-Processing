package com.ecnu.controller;

import com.ecnu.pojo.LineParam;
import com.ecnu.pojo.GeoParam;
import com.ecnu.service.BasicImageService;
import com.ecnu.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/basic")
public class BasicImageController {


    @Autowired
    BasicImageService basicImageService;


    @PostMapping("/geo")
    public Result Geo(@RequestParam("file") MultipartFile file, GeoParam param) {
        Map<String, String> map = basicImageService.GeoTransform(file, param);
        return Result.success(map);
    }

    @PostMapping("/geo/two")
    public Result GeoMulti(@RequestParam("file") MultipartFile file, @RequestParam("file2")MultipartFile file2, GeoParam param) {
        Map<String, String> map = basicImageService.GeoTransformMulti(file,file2,param);
        return Result.success(map);
    }

    @PostMapping("/line")
    public Result LineDetection(@RequestParam("file") MultipartFile file, LineParam param) {
        Map<String, String> map = basicImageService.LineDetection(file, param);
        return Result.success(map);
    }

    @PostMapping("/edge")
    public Result EdgeDetection(@RequestParam("file") MultipartFile file, @RequestParam("operator") String operator) {
        Map<String, String> map = basicImageService.EdgeDetection(file, operator);
        return Result.success(map);
    }

    @PostMapping("/morphology")
    public Result Morphology(@RequestParam("file") MultipartFile file, @RequestParam("len") int len) {
        Map<String, String> map = basicImageService.Morphology(file, len);
        return Result.success(map);
    }

    @PostMapping("/filter")
    public Result NoiseFilter(@RequestParam("file") MultipartFile file, @RequestParam("filter") String filter) {
        Map<String, String> map = basicImageService.NoiseFilter(file, filter);
        return Result.success(map);
    }

}
