package com.ecnu.util;

import com.ecnu.config.EnvInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyProgram {

    @Resource(name = "envInfo")
    private EnvInfo env;


    public List<String> pythonExecute(String prog, String[] argv){

        List<String> result = new ArrayList<>();
        try {
            String[] args = new String[2+argv.length];
            args[0] = env.getPycmd();
            args[1] = env.PythonPath+"/"+prog;
            for(int i=0; i<argv.length; ++i){
                args[i+2] = String.valueOf(argv[i]);
            }
            /**
              python3 /ProjectFile/img_project/python/fast-style-transfer/fast-style-transfer-master/evaluate.py --checkpoint  /ProjectFile/img_project/python/fast-style-transfer/checkpoints/la_muse.ckpt --in-path /ProjectFile/img_project/temp/img/1efe0dc9d25f46e3ab981537775f9b5c.jpg --out-path /ProjectFile/img_project/result/transfer_img/f4b174b7-4fa4-49d0-8a32-341ca117a7c4.jpg
             */

            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
            in.close();
            proc.waitFor();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> pythonExecute2(String prog, String[] argv, String path){

        List<String> result = new ArrayList<>();
        try {
            String[] args = new String[2+argv.length];
            args[0] = env.getPycmd();
            args[1] = env.PythonPath+"/"+prog;
            for(int i=0; i<argv.length; ++i){
                args[i+2] = String.valueOf(argv[i]);
            }
            /**
             python3 /ProjectFile/img_project/python/fast-style-transfer/fast-style-transfer-master/evaluate.py --checkpoint  /ProjectFile/img_project/python/fast-style-transfer/checkpoints/la_muse.ckpt --in-path /ProjectFile/img_project/temp/img/1efe0dc9d25f46e3ab981537775f9b5c.jpg --out-path /ProjectFile/img_project/result/transfer_img/f4b174b7-4fa4-49d0-8a32-341ca117a7c4.jpg
             */
            Process proc = Runtime.getRuntime().exec(args,null, new File(path));// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
            in.close();
            proc.waitFor();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
