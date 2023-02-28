package com.ecnu.service;

import com.ecnu.config.EnvInfo;
import com.ecnu.util.MyFileUtil;
import com.ecnu.util.MyProgram;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FastStyleTransferServiceImpl implements FastStyleTransferService{
    @Resource(name = "envInfo")
    private EnvInfo env;

    @Resource
    MyFileUtil mfu;

    @Resource
    MyProgram myProg;

    @Override
    public Map<String, String> imgTransfer(MultipartFile file, String style) {
        String inputImgPath = mfu.saveTempImg(file);
        if (inputImgPath==null) return null;
        String trainedNetworkPath;
        if (style.equals("qljst2"))
            trainedNetworkPath = env.PythonPath + "/fast-style-transfer/checkpoints/" + style ;
        else
            trainedNetworkPath = env.PythonPath + "/fast-style-transfer/checkpoints/" + style + ".ckpt";
        String outputImgPath = "/transfer_img/" + UUID.randomUUID() + ".jpg";
        String[] factors = new String[6];
        //python evaluate.py --checkpoint ../checkpoints/rain_princess.ckpt --in-path ../input_img/test1.jpg --out-path ../output_img/result1.jpg
        factors[0] = "--checkpoint";
        factors[1] = trainedNetworkPath;
        factors[2] = "--in-path";
        factors[3] = inputImgPath;
        factors[4] = "--out-path";
        factors[5] = env.TargetImagePath + outputImgPath;
        myProg.pythonExecute2("fast-style-transfer/fast-style-transfer-master/evaluate.py", factors, env.PythonPath+"/fast-style-transfer/fast-style-transfer-master");
        return new HashMap<String, String>(){{put("result", env.WebFilePath + outputImgPath);
            put("origin", getTmpFileWebPath(inputImgPath));}};
    }

    private String getTmpFileWebPath(String originImgPath){
        String substring = originImgPath.substring(originImgPath.lastIndexOf("/")+1);
        return env.WebFilePath + "/tmp/" + substring;
    }

}
