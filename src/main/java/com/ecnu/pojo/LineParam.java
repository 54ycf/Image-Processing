package com.ecnu.pojo;

import lombok.Data;

@Data
public class LineParam {

    private int threshold = 118;
    private int minLineLength = 80;
    private int maxLineGap = 15;
}
