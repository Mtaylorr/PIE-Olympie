package com.ensta.interfacegraphique.dao;

import com.ensta.interfacegraphique.exception.DaoException;
import com.ensta.interfacegraphique.model.Personnel;
import com.ensta.interfacegraphique.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDao {
    private static PersonnelDao instance;
    private PersonnelDao(){}

    public static PersonnelDao getInstance() {
        if(instance==null){
            instance=new PersonnelDao();
        }
        return instance;
    }

    public List<Personnel> getList() throws DaoException{
        String selectQuery = "SELECT personnel.id as idt, personnel.nom as name,prenom,salleId,x,y " +
                "FROM personnel,salle WHERE salle.id=salleId " +
                "ORDER BY nom,prenom";

        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Personnel> l = new ArrayList<Personnel>();
            while(rs.next()){
                l.add(new Personnel(rs.getInt("idt"),rs.getString("name"),rs.getString("prenom"),
                        rs.getString("salleId"),rs.getInt("x"),rs.getInt("y")));
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("personnels get list failed");
        }
    }
    public List<Personnel> getByString(String name) throws DaoException{
        String selectQuery = "SELECT personnel.id as idt, personnel.nom as name,prenom,salleId,x,y " +
                "FROM personnel,salle WHERE (salle.id=salleId) AND (personnel.nom LIKE ?  OR prenom LIKE ? OR salleId LIKE ? ) ;";
        try{
            name="%"+name+"%";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setString(1,name);
            selectPreparedStatement.setString(2,name);
            selectPreparedStatement.setString(3,name);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Personnel> l = new ArrayList<Personnel>();
            while(rs.next()){
                l.add(new Personnel(rs.getInt("idt"),rs.getString("name"),rs.getString("prenom"),
                        rs.getString("salleId"),rs.getInt("x"),rs.getInt("y")));
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("personnels get list failed");
        }
    }
    public Personnel getById(int id) throws DaoException{
        String selectQuery = "SELECT id,nom,prenom,salleId,x,y " +
                "FROM personnel,salle " +
                "WHERE id = ? " +
                "AND salle.id = salleId;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setInt(1,id);
            ResultSet rs = selectPreparedStatement.executeQuery();
            if(!rs.next())throw new DaoException("selecting personnel failed -dao");
            Personnel personnel = new Personnel(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),
                    rs.getString("salleId"),rs.getInt("x"),rs.getInt("y"));
            selectPreparedStatement.close();
            connection.close();
            return personnel;
        }catch (SQLException e){
            throw new DaoException("selecting personnel failed -dao");
        }
    }
    public int create(String nom, String prenom, String salleId) throws DaoException{
        String insertQuery = "INSERT INTO personnel(nom,prenom,salleId) VALUES(?,?,?);";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            insertStatement.setString(1,nom);
            insertStatement.setString(2,prenom);
            insertStatement.setString(3,salleId);

            int affectedRows = insertStatement.executeUpdate();
            if(affectedRows == 0)throw new DaoException("creating personnel failed - no affected rows");
            ResultSet rs  = insertStatement.getGeneratedKeys();
            int cnt = -1;
            if(rs.next()){
                cnt = rs.getInt(1);
            }
            connection.commit();
            insertStatement.close();
            connection.close();
            return cnt;
        }catch (SQLException e){
            throw new DaoException("creating personnel failed");
        }
    }
    public void update(Personnel personnel) throws DaoException{
        String updateQuery = "UPDATE personnel SET nom=?, prenom=?, salleId=? " +
                "WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1,personnel.getNom());
            updateStatement.setString(2,personnel.getPrenom());
            updateStatement.setString(3,personnel.getSalle());

            updateStatement.executeUpdate();
            connection.commit();
            updateStatement.close();
            connection.close();
        }catch (SQLException e){

        }
    }
    public void delete(int id) throws DaoException{
        String deleteQuery = "DELETE FROM personnel WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1,id);
            deleteStatement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e){

        }
    }
    public int count() throws DaoException{
        String selectQuery = "SELECT COUNT(id) AS count FROM personnel;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectStatement.executeQuery();
            rs.next();
            int cnt = rs.getInt("count");
            selectStatement.close();
            connection.close();
            return cnt;
        }catch (SQLException e){
            throw new DaoException("count personnel failed");
        }
    }
}
