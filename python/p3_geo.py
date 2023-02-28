import uuid
import cv2
import numpy as np
import sys
import matplotlib.pyplot as plt



img = cv2.imread(sys.argv[1]) #参数1：图片路径

op = sys.argv[11]
if op != "no":
    if op == "~":
        img = ~img
    else:
        img2 = cv2.imread(sys.argv[12])
        h0, w0 = img.shape[0:2]
        img2 = cv2.resize(img2, (w0,h0), interpolation = cv2.INTER_AREA)
        if op == "+":
            img = cv2.add(img,img2)
        elif op == "-":
            img = cv2.subtract(img,img2)
        elif op == "*":
            img = cv2.multiply(img,img2)
        elif op == "/":
            img = cv2.multiply(img,img2)
        elif op == "&":
            img = img & img2
        elif op == "|":
            img = img | img2

# 参数2是x的放大倍数，参数3是y的放大倍数，使用双线性插值法
img = cv2.resize(img,(0,0),fx=float(sys.argv[2]),fy=float(sys.argv[3]),interpolation=cv2.INTER_LINEAR)


height, width, channel = img.shape
# 构建移动矩阵,x轴左移参数4，y轴下移参数5
M = np.float32([[1,0,sys.argv[4]],[0,1,sys.argv[5]]])
img = cv2.warpAffine(img, M, (width, height))


# 构建矩阵，旋转角度为参数6度，缩放因子为参数7
M = cv2.getRotationMatrix2D((width,height),float(sys.argv[6]),float(sys.argv[7]))
img = cv2.warpAffine(img, M, (width, height))




#水平镜像 1 
#垂直镜像 0
#对角镜像 -1
flipCode = int(sys.argv[8])
if flipCode != 2:
    img = cv2.flip(img,flipCode)
uid = str(uuid.uuid1())
name = "/geo/" + uid + ".jpg"
cv2.imwrite("./result" + name, img)
print("result")
print(name)



zone = sys.argv[9]
if zone=="rgb":
    b = img[:, :, 0]  
    g = img[:, :, 1]  
    r = img[:, :, 2]  
    nameb = '/RGB/' + uid +'_b.jpg'
    nameg = '/RGB/' + uid +'_g.jpg'
    namer = '/RGB/' + uid +'_r.jpg'
    cv2.imwrite('./result' + nameb, b)  
    cv2.imwrite('./result' + nameg, g)  
    cv2.imwrite('./result' + namer, r)  
    print("RorH")
    print(nameb)
    print("GorS")
    print(nameg)
    print("BorV")
    print(namer)
if zone=="hsv":
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)  
    h = hsv[:,:,0]  
    s = hsv[:,:,1]  
    v = hsv[:,:,2] 
    nameh = "/HSV/" + uid + "_h.jpg"
    names = "/HSV/" + uid + "_s.jpg"
    namev = "/HSV/" + uid + "_v.jpg"
    cv2.imwrite("./result"+nameh, h)  
    cv2.imwrite("./result"+names, s)  
    cv2.imwrite("./result"+namev, v) 
    print("RorH")
    print(nameh)
    print("GorS")
    print(names)
    print("BorV")
    print(namev)




histogram = sys.argv[10]
if histogram=="multi":
    b,g,r=cv2.split(img)
    imgRGB=cv2.merge([r,g,b])
    color = ["r", "g", "b"]

    for index, c in enumerate(color):
        hist=cv2.calcHist([imgRGB],[index],None,[256],[0,255])
        plt.plot(hist, color=c)
        plt.xlim([0, 255])
    name = "/histogram/" + uid + ".jpg"
    plt.savefig("./result" + name)
    print("histogram")
    print(name)

if histogram=="grey":
    imgGrey = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    hist = cv2.calcHist([imgGrey],[0],None,[256],[0,255])
    plt.plot(hist)
    plt.xlim([0, 255])
    name = "/histogram/" + uid + ".jpg"
    plt.savefig("./result" + name)
    print("histogram")
    print(name)


