import sys
import uuid
import numpy as np
import cv2


def ave(image):
    output = np.zeros(image.shape, np.uint8)
    # 遍历图像，进行均值滤波
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            # 计算均值,完成对图片src的几何均值滤波
            ji = 1.0
            # 遍历滤波器内的像素值
            for n in range(-1,2):
                if 0 <= j+n < image.shape[1]:
                    ji = ji*image[i][j + n]
            # 滤波器的大小为1*3
            output[i][j] = pow(ji, 1.0/3)
    # 展示均值滤波后的图片
    name_ave = "/filter/" + str(uuid.uuid1()) + "_ave.jpg"
    cv2.imwrite("./result" + name_ave, output)
    print("result")
    print(name_ave)

def sort(image):
    b,g,r = cv2.split(image)
    r = sort2(r)
    g = sort2(g)
    b = sort2(b)
    output = cv2.merge([b,g,r])
    name_sort = "/filter/" + str(uuid.uuid1()) + "_sort.jpg"
    cv2.imwrite("./result" + name_sort,output)
    print("result")
    print(name_sort)
def sort2(image):
    output = np.zeros(image.shape, np.uint8)
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            # 最大值滤波器
            Max = 0
            for m in range(-1,2):
                for n in range(-1,2):
                    if 0 <= i + m < image.shape[0] and 0 <= j + n < image.shape[1]: 
                        # 通过比较判断是否需要更新最大值
                        if image[i+m][j+n] > Max:
                            # 更新最大值
                            Max = image[i+m][j+n]
            output[i][j] = Max
    return output


def selectivity(image):
    b,g,r = cv2.split(image)
    r = selectivity2(r)
    g = selectivity2(g)
    b = selectivity2(b)
    output = cv2.merge([b,g,r])
    name_select = "/filter/" + str(uuid.uuid1()) + "_select.jpg"
    cv2.imwrite("./result" + name_select, output)
    print("result")
    print(name_select)
def selectivity2(image):
    output = np.zeros(image.shape, np.uint8)
    Min = 100
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            if Min < image[i][j]:
                output[i][j] = image[i][j]
            else:
                output[i][j] = 0
    return output

if __name__ == '__main__':
    # 使用opencv读取图片
    image = cv2.imread(sys.argv[1])
    filtor = sys.argv[2]
    if filtor == "average":
        ave(image)
    elif filtor == "sort":
        sort(image)
    elif filtor == "selectivity":
        selectivity(image)