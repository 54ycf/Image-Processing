package com.ecnu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Value("${pycmd}")
    private String pycmd;
    private final String RootPath = System.getProperty("user.dir");
    private final String PythonPath = RootPath + "/python";
    private final String TempPath = RootPath + "/temp";
    private final String OriginImagePath =TempPath + "/img";
    private final String TargetImagePath = RootPath + "/result";
    @Value("${web-file}")
    private String WebFilePath;


    @Bean("envInfo")
    public EnvInfo envInit(){
        EnvInfo info = new EnvInfo();
        info.setPycmd(pycmd);
        info.setRootPath(RootPath);
        info.setTempPath(TempPath);
        info.setPythonPath(PythonPath);
        info.setOriginImagePath(OriginImagePath);
        info.setTargetImagePath(TargetImagePath);
        info.setWebFilePath(WebFilePath);
        return info;
    }
}
