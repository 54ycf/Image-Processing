import sys
import uuid
import cv2
import numpy as np

def robs(img):
    # 1. 灰度化处理图像
    grayImage = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # 2. Roberts算子
    kernelx = np.array([[-1,0],[0,1]], dtype=int)
    kernely = np.array([[0,-1],[1,0]], dtype=int)
    # 3. 卷积操作
    x = cv2.filter2D(grayImage, cv2.CV_16S, kernelx)
    y = cv2.filter2D(grayImage, cv2.CV_16S, kernely)
    # 4. 数据格式转换
    absX = cv2.convertScaleAbs(x)
    absY = cv2.convertScaleAbs(y) 
    Roberts = cv2.addWeighted(absX,0.5,absY,0.5,0)
    # 保存图像
    name = "/edge/" + str(uuid.uuid1()) + "_robs.jpg"
    cv2.imwrite("./result" + name ,Roberts)
    return name

def sob(img):
    # 1. 灰度化处理图像
    grayImage = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # 2. 求Sobel 算子
    kernelx = cv2.Sobel(grayImage, cv2.CV_16S, 1, 0)
    kernely = cv2.Sobel(grayImage, cv2.CV_16S, 0, 1)
    x = cv2.filter2D(grayImage, cv2.CV_16S, kernelx)
    y = cv2.filter2D(grayImage, cv2.CV_16S, kernely)
    # 3. 数据格式转换
    absX = cv2.convertScaleAbs(x)
    absY = cv2.convertScaleAbs(y)
    # 4. 组合图像
    Sobel = cv2.addWeighted(absX, 0.5, absY, 0.5, 0)
    # 保存图像
    name = "/edge/" + str(uuid.uuid1()) + "_sob.jpg"
    cv2.imwrite("./result" + name ,Sobel)
    return name

def lap(img):
    # 1. 灰度化处理图像
    grayImage = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # 2. 高斯滤波
    grayImage = cv2.GaussianBlur(grayImage, (5,5), 0, 0)
    # 3. 拉普拉斯算法
    dst = cv2.Laplacian(grayImage, cv2.CV_16S, ksize=3)
    # 4. 数据格式转换
    Laplacian = cv2.convertScaleAbs(dst)
    # 保存图像
    name = "/edge/" + str(uuid.uuid1()) + "_lap.jpg"
    cv2.imwrite("./result" + name ,Laplacian)
    return name


def _log(img):
    # 1. 灰度转换  
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)  
    # 2. 边缘扩充处理图像并使用高斯滤波处理该图像  
    image = cv2.copyMakeBorder(img, 2,2,2,2, borderType=cv2.BORDER_REPLICATE)  
    image = cv2.GaussianBlur(image, (3,3), 0,0)
    # 3. 使用Numpy定义LoG算子  
    m1 = np.array([ [0,   0, -1,  0,  0], 
                    [0,  -1, -2, -1,  0], 
                    [-1, -2, 16, -2, -1],
                    [0,  -1, -2, -1,  0],
                    [0,   0, -1,  0,  0] ])  # LoG算子模板
    # 4. 卷积运算  
    rows,cols = image.shape[:2]  
    image1 = np.zeros(image.shape)
    # 为了使卷积对每个像素都进行运算，原图像的边缘像素要对准模板的中心。  
    # 由于图像边缘扩大了2像素，因此要从位置2到行(列)-2  
    for k in range(0, 2):   
        for i in range(2, rows - 2):  
            for j in range(2, cols - 2):  
                image1[i, j] = np.sum((m1 * image[i - 2:i + 3, j - 2:j + 3, k]))
    # 5. 数据格式转换  
    image1 = cv2.convertScaleAbs(image1)
    name = "/edge/" + str(uuid.uuid1()) + "_log.jpg"
    cv2.imwrite("./result" + name ,image1)
    return name

# Canny算子
def cny(img):
    # 1. 高斯滤波
    img = cv2.GaussianBlur(img, (3,3), 0)
    # 2. 灰度转换
    img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  
    # 3. 求x，y方向的Sobel算子
    gradx = cv2.Sobel(img, cv2.CV_16SC1, 1, 0)  
    grady = cv2.Sobel(img, cv2.CV_16SC1, 0, 1)  
    # 4. 使用Canny函数处理图像，x,y分别是3求出来的梯度，低阈值50，高阈值150
    edge_output = cv2.Canny(gradx, grady, 50, 150)  
    name = "/edge/" + str(uuid.uuid1()) + "_cny.jpg"
    cv2.imwrite("./result" + name , edge_output)
    return name

if __name__ == '__main__':
    img = cv2.imread(sys.argv[1])
    oper = sys.argv[2]
    if oper == "Roberts":
        repath = robs(img)
    elif oper == "Sobel":
        repath = sob(img)
    elif oper == "Laplacian":
        repath = lap(img)
    elif oper == "LoG":
        repath = _log(img)
    elif oper == "Canny":
        repath = cny(img)
    print("edge")
    print(repath)