package com.ensta.interfacegraphique.dao;

import com.ensta.interfacegraphique.exception.DaoException;
import com.ensta.interfacegraphique.model.Salle;
import com.ensta.interfacegraphique.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDao {
    private static SalleDao instance;
    private SalleDao(){}

    public static SalleDao getInstance() {
        if(instance==null){
            instance=new SalleDao();
        }
        return instance;
    }

    public List<Salle> getList() throws DaoException{
        String selectQuery = "SELECT id,nom,etage,x,y " +
                "FROM salle ;";

        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Salle> l = new ArrayList<Salle>();
            while(rs.next()){
                l.add(new Salle(rs.getString("id"),rs.getString("nom"),rs.getInt("etage"),
                        rs.getInt("x"),rs.getInt("y")));
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Salle get list failed");
        }
    }
    public List<Salle> getByNameorId(String nameOrId) throws DaoException{
        String selectQuery = "SELECT id,nom,etage,x,y " +
                "FROM salle " +
                "WHERE id LIKE ? or nom LIKE ? " +
                " ORDER BY id;";

        try{
            nameOrId="%"+nameOrId+"%";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setString(1,nameOrId);
            selectPreparedStatement.setString(2,nameOrId);
            ResultSet rs = selectPreparedStatement.executeQuery();
            List<Salle> l = new ArrayList<Salle>();
            while(rs.next()){
                l.add(new Salle(rs.getString("id"),rs.getString("nom"),rs.getInt("etage"),
                        rs.getInt("x"),rs.getInt("y")));
            }
            selectPreparedStatement.close();
            connection.close();
            return l;
        }catch (SQLException e){
            throw new DaoException("Salle get list failed");
        }
    }
    public Salle getById(String id) throws DaoException{
        String selectQuery = "SELECT id,nom,etage,x,y " +
                "FROM salle " +
                "WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(selectQuery);
            selectPreparedStatement.setString(1,id);
            ResultSet rs = selectPreparedStatement.executeQuery();
            if(!rs.next())throw new DaoException("selecting salle failed -dao");
            Salle salle = new Salle(rs.getString("id"),rs.getString("nom"),rs.getInt("etage"),
                    rs.getInt("x"),rs.getInt("y"));
            selectPreparedStatement.close();
            connection.close();
            return salle;
        }catch (SQLException e){
            throw new DaoException("selecting salle failed -dao");
        }
    }
    public int create(String nom, int etage, int x, int y) throws DaoException{
        String insertQuery = "INSERT INTO personnel(nom,etage,x,y) VALUES(?,?,?,?);";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            insertStatement.setString(1,nom);
            insertStatement.setInt(2,etage);
            insertStatement.setInt(3,x);
            insertStatement.setInt(4,y);

            int affectedRows = insertStatement.executeUpdate();
            if(affectedRows == 0)throw new DaoException("creating salle failed - no affected rows");
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
            throw new DaoException("creating salle failed");
        }
    }
    public void update(Salle salle) throws DaoException{
        String updateQuery = "UPDATE salle SET nom=?, etage=?, x=?, y=? " +
                "WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1,salle.getId());
            updateStatement.setString(2,salle.getNom());
            updateStatement.setInt(3,salle.getEtage());
            updateStatement.setInt(4,salle.getX());
            updateStatement.setInt(5,salle.getY());

            updateStatement.executeUpdate();
            connection.commit();
            updateStatement.close();
            connection.close();
        }catch (SQLException e){

        }
    }
    public void delete(String id) throws DaoException{
        String deleteQuery = "DELETE FROM salle WHERE id = ?;";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1,id);
            deleteStatement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e){

        }
    }
    public int count() throws DaoException{
        String selectQuery = "SELECT COUNT(id) AS count FROM salle;";
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
            throw new DaoException("count salle failed");
        }
    }
}
