# Description du dossier :
Le dossier contient le code de déplacement du robot dans la simulateur Gazebo du ROS.

# Reglage de l'environnement : 
## Création du Pkg de déplacement 
On effectue les commandes suivantes : 
*  roscd
*  cd .. 
* cd src
* pwd      ( il faut voir "/home/user/catkin_ws/src" affichié dans le console)
* catkin_create_pkg moverobot rospy
* cd ..
* catkin_make
* cd src
* cd moverobot
* cd src
* touch move.py
* chmod +x move.py
* move the content of the code Dep.py here in the folder to move.py
## Exécution du pkg  de déplacement
Pour exécuter le pkg , il suffit de taper la commande suivante : 
* rosrun moverobot move.py
