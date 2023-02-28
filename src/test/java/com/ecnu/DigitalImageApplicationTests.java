package com.ecnu;

import com.ecnu.config.EnvInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DigitalImageApplicationTests {
    @Resource(name = "envInfo")
    private EnvInfo env;

    @Test
    void contextLoads() {
        String originImgPath = "/ab/cd/ef/tmp/123.jpg";
        String substring = originImgPath.substring(originImgPath.lastIndexOf("/")+1);
        String s = env.WebFilePath + "/tmp/" + substring;
        System.out.println(s);
    }

}
