#!/usr/bin/env python
# importation des libraries
import rospy
from math import sqrt
import time
from nav_msgs.msg import Odometry
from tf.transformations import euler_from_quaternion
from geometry_msgs.msg import Twist
import math
import os

#definition des variables pour la position du robot
roll = pitch = yaw = 0.0

xpose=0 # position x du robot
ypose=0 # position y dy robot

def get_pos (msg):
    # fonction pour le calcul de la position du robot
    global roll, pitch, yaw, xpose, ypose
    orientation_q = msg.pose.pose.orientation
    orientation_list = [orientation_q.x, orientation_q.y, orientation_q.z, orientation_q.w]
    (roll, pitch, yaw) = euler_from_quaternion (orientation_list)
    positionlist = msg.pose.pose.position
    xpose, ypose = positionlist.x, positionlist.y
	

def turnright(angle):
    target_angle=angle*math.pi/180 # conversion de l'angle du degré vers le radian
    command= Twist() # creation de la commande du robot 
    KP=0.2 # vitesse de rotation
    command.angular.z = KP # assignement du vitesse de rotation pour la composante z 
    while  (not rospy.is_shutdown()) and not rospy.is_shutdown():
        t0 = rospy.Time.now().to_sec() # temps de départ de rotation
        current_angle = 0 # angle tourné au début de roation (=0)
        while(current_angle<target_angle): # tant que l'angle qu'il a tourné déjà n'atteint pas l'objective , on continue a tourner
            pub.publish(command) # publier la commande 
            t1=rospy.Time.now().to_sec() # le temps à cette étape
            current_angle= abs(KP)*(t1-t0) # le calcul de l'angle tourné à cette étape en utilisant la formule (t_cur - t_init) * | vitesse de rotation | 
        command.angular.z = 0 # on annule la roation selon z
        pub.publish(command) # arrêter le robot de tourner 
        break
        
    

def turnleft(angle):
    # fonctionnement similaire à la fonction turnright
    target_angle=angle*math.pi/180
    command= Twist()
    KP=-0.2
    command.angular.z = KP
    while  (not rospy.is_shutdown()) :
        t0 = rospy.Time.now().to_sec()
        current_angle = 0
        while(current_angle<target_angle) and not rospy.is_shutdown():
            pub.publish(command)
            t1=rospy.Time.now().to_sec()
            current_angle= abs(KP)*(t1-t0)
        command.angular.z = 0
        pub.publish(command)
        break
    

def forward(dist):
    command= Twist() # création de la commande
    xV=0.4 # vitesse linéaire 
    distance=dist # distance qu'on va parcourir
    command.linear.x = xV # assignement de la vitesse dans la commande 
    speed = abs(xV)  # la vitesse 
    while  (not rospy.is_shutdown()) :
        t0 = rospy.Time.now().to_sec() # temps de départ de déplacement
        current_distance = 0 # distance parcouru à ce moment (=0)
        while(current_distance<distance and not rospy.is_shutdown()):
            pub.publish(command) # publier la commande (déplacer le robot)
            t1=rospy.Time.now().to_sec() # le temps à cette étape
            current_distance= speed*(t1-t0) # le calcul de la distance parcouru en utilisant la formule  d = (t_cur - t_init)*vitesse
        command.linear.x=0 # annuler la vitesse linéaire
        pub.publish(command) # arrêter le robot de se déplacer
        break

def getx():
    return -round(xpose/pas) # retourner la position x du robot

def gety():
    return  round(ypose/pas) # retourner la position y du robot
	
rospy.init_node('my_quaternion_to_euler')


sub = rospy.Subscriber ('/odom', Odometry, get_pos)
pub = rospy.Publisher('/cmd_vel', Twist, queue_size=1)

command = Twist()

r = rospy.Rate(10)

di = "codedep/" # le directory ou se situe le PathFniding.out et le map.bin
c = input("initiate : ready (y,n) : ") # just pour commancer le déplacement du robot (il faut écrire "y" avec quotes) 

while(c!='y') :
    c = input("initiate : ready (y,n) : ")

pas=1 # le pas avec lequelle se déplace le robot à chaque étape  
k=2
xf , yf = 2 , 2 # position finale du robot ( c'est donée explicitement pour le moment mais c'est censé donnée par l'utilisateur ) 
while (not rospy.is_shutdown()) and (getx()!=xf or gety()!=yf):
    xs ,ys = getx(), gety()
    xs = int(xs)
    ys = int(ys)
    cmd = "./{}PathFinding.out {}map.bin {} {} {} {} {}path.txt".format(di,di,ys,xs,xf,yf,di)# formulation de la commande de calcul du path du position courante vers la position finale
    os.system(cmd)# on efface le fichier pour le nouveau déplacement
    while (not rospy.is_shutdown()) and (os.path.isfile(di+"path.txt")==0):
        pass

    f = open("{}path.txt".format(di),"r")
    path = f.readline()
    i=0 # nombre d'itérations
    for c in path :# on itére sur les instructions de déplacement 
        if(i==k): # si on a fait K déplacement on arrête pour recalculer le chemin
            break
        i+=1 # nombre d'iterations incrémente par 1
        ind = int(c) # on convertit l'entier donné comme chaine en entier 
	tar_angle = ind * 45 # on calcule l'angle correspondante à la direction voulu 
	angle_par =  tar_angle-yaw*180/math.pi # calcul de l'angle qu'il faut tourner pour que la direction du robot et de l'instruction coincide
	if(angle_par>180):  # s'il l'angle est > 180 donc on tourne à gauche c'est mieux
		angle_par-=360 
	if(angle_par>0):
		turnright(angle_par) # si l'angle position on tourne à droite
	else :
		turnleft(-angle_par) # sinon on tourne à gauche
	dist = pas # distance qu'on va parcourir
	if(ind%2):
		dist*=math.sqrt(2) # si la direction est multiple de 45 degré alors on va déplacer pas * sqrt(2)
	forward(dist) # déplacer le robot dans cette direction d'un pas

    f.close()# on ferme le fichier
    os.system("rm {}path.txt".format(di)) #on suprimme le fichier path.txt

