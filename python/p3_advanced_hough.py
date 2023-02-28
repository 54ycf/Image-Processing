import uuid
import cv2
import numpy as np
import sys

img = cv2.imread(sys.argv[1])

img = cv2.GaussianBlur(img, (3, 3), 0)
edges = cv2.Canny(img, 50, 150, apertureSize=3)


# 使用HoughLines算法
# rho为1 
# theta为np.pi/2 
# threshold为 默认118 参数2
# 其他的默认
rho = 1
theta = np.pi/2
threshold = int(sys.argv[2])
lines = cv2.HoughLines(edges, 1, np.pi/2, threshold)

#########  end  ##########

result = img.copy()
for i_line in lines:
    for line in i_line:
        rho = line[0]
        theta = line[1]
        if (theta < (np.pi / 4.)) or (theta > (3. * np.pi / 4.0)):  # 垂直直线
            pt1 = (int(rho / np.cos(theta)), 0)
            pt2 = (int((rho - result.shape[0] * np.sin(theta)) / np.cos(theta)), result.shape[0])
            cv2.line(result, pt1, pt2, (0, 0, 255))
        else:
            pt1 = (0, int(rho / np.sin(theta)))
            pt2 = (result.shape[1], int((rho - result.shape[1] * np.cos(theta)) / np.sin(theta)))
            cv2.line(result, pt1, pt2, (0, 0, 255), 1)


minLineLength = int(sys.argv[3]) #参数3
maxLineGap = int(sys.argv[4]) #参数4


# 使用HoughLinesP算法
# rho为1
# theta为np.pi/2
# threshold为 118
# 并设置上面提供的minLineLength和maxLineGap
# 其他的默认
#########  Begin #########
theta = np.pi/180
# threshold = 80
linesP = cv2.HoughLinesP(edges, 1, np.pi / 180, threshold, minLineLength, maxLineGap)

#########  end  ##########


result_P = img.copy()
for i_P in linesP:
    for x1, y1, x2, y2 in i_P:
        cv2.line(result_P, (x1, y1), (x2, y2), (0, 255, 0), 3)

uid = str(uuid.uuid1())
name = "/hough/" + uid + ".jpg"
namep = "/hough/" + uid + "_p.jpg"
cv2.imwrite('./result' + name, result)
cv2.imwrite('./result' + namep, result_P)
print("res1")
print(name)
print("res2")
print(namep)