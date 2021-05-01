package com.ensta.interfacegraphique.utils;

import com.ensta.interfacegraphique.persistence.ConnectionManager;
import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "interfaceGraphiqueDatabase", true);
            insertWithPreparedStatement();
            System.out.println("Success !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;

        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE salle(id VARCHAR(5) primary key , nom VARCHAR(100), etage INT, x INT, y INT);");
        createTablesQueries.add("CREATE TABLE personnel(id INT primary key auto_increment, nom VARCHAR(100), prenom VARCHAR(100), salleId VARCHAR(5) ;");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
                createPreparedStatement = connection.prepareStatement(createQuery);
                createPreparedStatement.executeUpdate();
                createPreparedStatement.close();
            }

            // Ajout de plusieurs enregistrement avec Statement
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0001', 'bureau', 0, 5, 6);");
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0002', 'bureau', 0, 10, 18);");
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0003', 'bureau', 0, 25, 25);");
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0004', 'bureau', 0, 8, 9);");
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0123', 'INFO', 0, 45, 53);");
            stmt.execute("INSERT INTO salle(id,nom,etage,x,y) VALUES('0124', 'SALLE', 0, 48, 54);");

            stmt.execute("INSERT INTO personnel(nom,prenom,salleId) VALUES('abc','aze','0001');");
            stmt.execute("INSERT INTO personnel(nom,prenom,salleId) VALUES('aaa','bbb','0002');");
            stmt.execute("INSERT INTO personnel(nom,prenom,salleId) VALUES('abz','aaa','0003');");


            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}