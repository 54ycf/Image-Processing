package com.ecnu.pojo;

import lombok.Data;

@Data
public class GeoParam {

    private double fx = 1; //水平放大倍数
    private double fy = 1; //垂直放大倍数
    private int sx = 0; //向左平移像素
    private int sy = 0; //向下平移像素
    private double angle = 0; //旋转角度
    private double factor = 1; //缩放因子
    private int mirror = 2; //镜像
    private String zone = "rgb"; // 色彩空间。0 rgb，1 hsv
    private String histogram = "grey"; //直方图。grey 灰度，multi 彩色
    private String op = "no";
}
