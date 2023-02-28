import sys
import uuid
import cv2


# 图像的二值化
src = cv2.imread(sys.argv[1], cv2.IMREAD_UNCHANGED)

len = int(sys.argv[2])

# 使用一个lenxlen的交叉型结构元（核心在几何中心）对二值图片src进行腐蚀
kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (len, len))


erosion = cv2.erode(src, kernel)

# 进行膨胀
dilation = cv2.dilate(src, kernel)  

# 进行开运算
open = cv2.morphologyEx(src, cv2.MORPH_OPEN, kernel) 

# 进行闭运算
close = cv2.morphologyEx(src, cv2.MORPH_CLOSE, kernel) 

# 图片保存
uid = str(uuid.uuid1())
name_ero = "/morphology/" + uid + "_ero.jpg"
name_dil = "/morphology/" + uid + "_dil.jpg"
name_ope = "/morphology/" + uid + "_ope.jpg"
name_clo = "/morphology/" + uid + "_clo.jpg"
cv2.imwrite("./result" + name_ero, erosion)
cv2.imwrite("./result" + name_dil, dilation)
cv2.imwrite("./result" + name_ope, open)
cv2.imwrite("./result" + name_clo, close)
print("erosion")
print(name_ero)
print("dilation")
print(name_dil)
print("open")
print(name_ope)
print("close")
print(name_clo)
