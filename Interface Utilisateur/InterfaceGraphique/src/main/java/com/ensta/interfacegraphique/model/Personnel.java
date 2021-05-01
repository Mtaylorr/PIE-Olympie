package com.ensta.interfacegraphique.model;

public class Personnel {
    int id;
    String nom,prenom;
    String salle;
    int x,y;
    public Personnel(int i,String n,String p,String s,int xx,int yy){
        id=i;
        nom=n;
        prenom=p;
        salle=s;
        x=xx;
        y=yy;
    }
    public void setNom(String n){
        nom=n;
    }
    public void setPrenom(String p){
        prenom=p;
    }
    public void setSalle(String s){
        salle=s;
    }
    public int getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSalle() {
        return salle;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +"\n"+
                ", nom='" + nom + '\'' +"\n"+
                ", prenom='" + prenom + '\'' +"\n"+
                ", salle='" + salle + '\'' +"\n"+
                "}\n";
    }
}
