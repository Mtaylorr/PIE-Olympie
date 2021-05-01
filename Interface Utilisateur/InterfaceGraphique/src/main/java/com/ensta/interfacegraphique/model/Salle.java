package com.ensta.interfacegraphique.model;

public class Salle {
    String id;
    String nom;
    int etage;
    int x,y;
    public Salle(String i,String n,int e,int xx,int yy){
        id=i;
        nom=n;
        etage=e;
        x=xx;
        y=yy;
    }

    public void setNom(String n){
        nom=n;
    }
    public void setEtage(int etage){
        this.etage=etage;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public int getEtage() {
        return etage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +"\n"+
                ", nom='" + nom + '\'' +"\n"+
                ", etage='" + etage + '\'' +"\n"+
                ", x='" + x + '\'' +"\n"+
                ", y='" + y + '\'' +"\n"+
                "}\n";
    }
}
