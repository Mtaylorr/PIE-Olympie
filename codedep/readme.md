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

\\

Avant d'exécuter le pkg il faut faire d'autre réglages : 
* Importer le code de PathFinding dans ROS (  wget "link_of_source_code" ) 
* se déplacer dans le répertoire du code de PathFinding  (cd PathFinding)
* compiler le code : g++ -std=c++17 -I headers src/\*.cpp -o PathFinding.out
* remplacer la variable "di" dans le code move.py par ce qui est affiché par la commande pwd 
## Exécution du pkg  de déplacement
Pour exécuter le pkg , il suffit de taper la commande suivante : 
* rosrun moverobot move.py
