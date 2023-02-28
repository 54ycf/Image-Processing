package com.ecnu.util;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 一般参数（form-data）校验绑定异常处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result bindException(BindException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();   // 获取所有的错误信息
        StringBuilder msg = new StringBuilder();
        allErrors.forEach(e -> {
            msg.append(e.getDefaultMessage()).append(" ");
            System.out.println(e.getDefaultMessage());
        });
        return Result.error(String.valueOf(msg));
    }

    /**
     * JSON参数校验绑定异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors(); // 获取所有的错误信息
        StringBuilder msg = new StringBuilder();
        allErrors.forEach(e -> {
            msg.append(e.getDefaultMessage()).append(" ");
            System.out.println(e.getDefaultMessage());
        });
        return Result.error(String.valueOf(msg));
    }

    @ExceptionHandler(value = FileSizeLimitExceededException.class)
    @ResponseBody
    public Result fileSizeException(FileSizeLimitExceededException ex) {
        ex.printStackTrace();
        return Result.error("图片大小请勿超过10MB");
    }


    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result otherException(Exception ex) {
        ex.printStackTrace();
        return Result.error("server inner error!\n"+ex.getMessage());
    }
}