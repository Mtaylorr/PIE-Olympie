#!/usr/bin/env python
import rospy
from math import sqrt
import time
from nav_msgs.msg import Odometry
from tf.transformations import euler_from_quaternion
from geometry_msgs.msg import Twist
import math
import os

roll = pitch = yaw = 0.0
target_angle = 0
kP = 1
xpose=0
ypose=0
def get_pos (msg):
    global roll, pitch, yaw, xpose, ypose
    orientation_q = msg.pose.pose.orientation
    orientation_list = [orientation_q.x, orientation_q.y, orientation_q.z, orientation_q.w]
    (roll, pitch, yaw) = euler_from_quaternion (orientation_list)
    positionlist = msg.pose.pose.position
    xpose, ypose = positionlist.x, positionlist.y
    #print yaw

def get(x, y):
    a = abs(y-x)
    b = 2*math.pi-abs(y-x)
    return min(a,b)

def initiate():
    global target_angle
    global roll, pitch, yaw, xpose, ypose
    target_angle+=0
    KP = 1
    print(abs(target_angle-180*yaw/math.pi))
    while  (not rospy.is_shutdown()) and abs(target_angle-180*yaw/math.pi)>1e-3:
        target_rad = target_angle * math.pi/180
        command.angular.z = kP  * (target_rad - yaw)
        pub.publish(command)
        r.sleep()
    command.angular.z = 0
    pub.publish(command)
    print(yaw)

def turnright(angle):
    global roll, pitch, yaw, xpose, ypose
    global target_angle
    target_angle=angle*math.pi/180
    command= Twist()
    KP=0.2
    command.angular.z = KP
    while  (not rospy.is_shutdown()) :
        t0 = rospy.Time.now().to_sec()
        current_angle = 0
        while(current_angle<target_angle):
            pub.publish(command)
            t1=rospy.Time.now().to_sec()
            current_angle= abs(KP)*(t1-t0)
        command.angular.z = 0
        pub.publish(command)
        break
        
    

def turnleft(angle):
    global roll, pitch, yaw, xpose, ypose
    global target_angle
    target_angle=angle*math.pi/180
    command= Twist()
    KP=-0.2
    command.angular.z = KP
    while  (not rospy.is_shutdown()) :
        t0 = rospy.Time.now().to_sec()
        current_angle = 0
        while(current_angle<target_angle):
            pub.publish(command)
            t1=rospy.Time.now().to_sec()
            current_angle= abs(KP)*(t1-t0)
        command.angular.z = 0
        pub.publish(command)
        break
    

def forward(dist):
    global roll, pitch, yaw, xpose, ypose
    global target_angle
    command= Twist()
    xV=0.4
    yV=0
    distance=dist
    command.linear.x = xV
    command.linear.y = yV
    speed = abs(xV)
    while  (not rospy.is_shutdown()) :
        t0 = rospy.Time.now().to_sec()
        current_distance = 0
        while(current_distance<distance):
            pub.publish(command)
            t1=rospy.Time.now().to_sec()
            current_distance= speed*(t1-t0)
            #print(current_distance)
        command.linear.x=0
        command.linear.y=0
        pub.publish(command)
        break

def getx():
    return 0

def gety():
    return 0

rospy.init_node('my_quaternion_to_euler')

sub = rospy.Subscriber ('/odom', Odometry, get_pos)
pub = rospy.Publisher('/cmd_vel', Twist, queue_size=1)
command= Twist()
r = rospy.Rate(10)
di = "codedep/"
c = input("initiate : ready (y,n) : ")
while(c!='y') :
    c = input("initiate : ready (y,n) : ")
initiate()
xf , yf = 2 , 2
xs ,ys = getx(), gety()
cmd = "./{}PathFinding.out {}map.bin {} {} {} {} {}path.txt".format(di,di,xs,ys,xf,yf,di)
print(cmd)
os.system(cmd)
while(os.path.isfile(di+"path.txt")==0):
	pass

f = open("{}path.txt".format(di),"r")
path = f.readline()
pas=1
print(path)
for c in path :
	ind = int(c)
	tar_angle = ind * 45
	angle_par =  tar_angle-yaw*180/math.pi
	if(angle_par>180):
		angle_par-=360
	if(angle_par>0):
		turnright(angle_par)
	else :
		turnleft(-angle_par)
	dist = pas
	if(ind%2):
		dist*=math.sqrt(2)
	forward(dist)

f.close()
os.system("rm {}path.txt".format(di))
