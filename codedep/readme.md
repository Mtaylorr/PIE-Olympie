# Description du dossier :
Le dossier contient le code de déplacement du robot dans la simulateur Gazebo du ROS.

# Reglage de l'environnement : 
## Création du Pkg de déplacement 
On effectue les commandes suivantes : 
*  roscd
*  cd .. 
3 - cd src
4 - pwd      ( il faut voir "/home/user/catkin_ws/src" affichié dans le console)
5 - catkin_create_pkg moverobot rospy
6 - cd ..
7 - catkin_make
8 - cd src
9 - cd moverobot
10 - cd src
11 - touch move.py
12 - chmod +x move.py
13 - move the content of the code Dep.py here in the folder to move.py
## Exécution du pkg  de déplacement
Pour exécuter le pkg , il suffit de taper la commande suivante : 
rosrun moverobot move.py
