package com.ecnu.service;

import com.ecnu.config.EnvInfo;
import com.ecnu.pojo.LineParam;
import com.ecnu.pojo.GeoParam;
import com.ecnu.util.MyFileUtil;
import com.ecnu.util.MyProgram;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasicImageServiceImpl implements BasicImageService {

    @Resource(name = "envInfo")
    private EnvInfo env;

    @Resource
    MyFileUtil mfu;
    
    @Resource
    MyProgram myProg;
    


    @Override
    public Map<String, String> GeoTransform(MultipartFile file, GeoParam param) {
        String originImgPath = mfu.saveTempImg(file);
        if (originImgPath==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[11];
        factors[0] = originImgPath;
        f1(factors,param);
        return execute("p3_geo.py", factors, originImgPath);
    }

    @Override
    public Map<String, String> GeoTransformMulti(MultipartFile file, MultipartFile file2, GeoParam param) {
        String originImgPath = mfu.saveTempImg(file);
        String originImgPath2 = mfu.saveTempImg(file2);
        if (originImgPath==null || originImgPath2==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[12];
        factors[0] = originImgPath;
        factors[11] = originImgPath2;
        f1(factors,param);
        Map<String, String> map = execute("p3_geo.py", factors, originImgPath);
        map.put("origin2", getTmpFileWebPath(originImgPath2));
        return map;
    }

    @Override
    public Map<String, String> LineDetection(MultipartFile file, LineParam param) {
        String originImgPath = mfu.saveTempImg(file);
        if (originImgPath==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[4];
        factors[0] = originImgPath;
        factors[1] = String.valueOf(param.getThreshold());
        factors[2] = String.valueOf(param.getMinLineLength());
        factors[3] = String.valueOf(param.getMaxLineGap());
        return execute("p3_advanced_hough.py", factors, originImgPath);
    }

    @Override
    public Map<String, String> EdgeDetection(MultipartFile file, String operator) {
        String originImgPath = mfu.saveTempImg(file);
        if (originImgPath==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[2];
        factors[0] = originImgPath;
        factors[1] = operator;
        return execute("p3_advanced_edge.py", factors, originImgPath);
    }

    @Override
    public Map<String, String> Morphology(MultipartFile file, int len) {
        String originImgPath = mfu.saveTempImg(file);
        if (originImgPath==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[2];
        factors[0] = originImgPath;
        factors[1] = String.valueOf(len);
        return execute("p3_advanced_morphology.py", factors, originImgPath);
    }

    @Override
    public Map<String, String> NoiseFilter(MultipartFile file, String filter) {
        String originImgPath = mfu.saveTempImg(file);
        if (originImgPath==null) return null;
        //参数是原图的绝对路径，其他操纵因子，返回文件名
        String[] factors = new String[2];
        factors[0] = originImgPath;
        factors[1] = filter;
        return execute("p3_advanced_filter.py", factors, originImgPath);
    }

    private void f1(String[] factors, GeoParam param){
        factors[1] = String.valueOf(param.getFx());
        factors[2] = String.valueOf(param.getFy());
        factors[3] = String.valueOf(param.getSx());
        factors[4] = String.valueOf(param.getSy());
        factors[5] = String.valueOf(param.getAngle());
        factors[6] = String.valueOf(param.getFactor());
        factors[7] = String.valueOf(param.getMirror());
        factors[8] = String.valueOf(param.getZone());
        factors[9] = String.valueOf(param.getHistogram());
        factors[10] = String.valueOf(param.getOp());
    }

    private Map<String,String> execute(String py, String[] factors, String originImgPath){
        List<String> info = myProg.pythonExecute(py, factors);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < info.size(); i=i+2) {
            map.put(info.get(i), env.WebFilePath + info.get(i + 1));
        }
        map.put("origin", getTmpFileWebPath(originImgPath));
        return map;
    }

    private String getTmpFileWebPath(String originImgPath){
        String substring = originImgPath.substring(originImgPath.lastIndexOf("/")+1);
        return env.WebFilePath + "/tmp/" + substring;
    }

}
