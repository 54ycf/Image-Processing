package com.ecnu.config;

import lombok.Data;

@Data
public class EnvInfo {
    public String pycmd;

    public String RootPath;
    public String PythonPath;
    public String TempPath;
    public String OriginImagePath;
    public String TargetImagePath;
    public String WebFilePath;
}
