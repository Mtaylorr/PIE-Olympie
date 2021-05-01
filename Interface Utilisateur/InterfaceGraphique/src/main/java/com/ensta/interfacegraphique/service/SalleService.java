package com.ensta.interfacegraphique.service;

import com.ensta.interfacegraphique.dao.SalleDao;
import com.ensta.interfacegraphique.exception.DaoException;
import com.ensta.interfacegraphique.exception.ServiceException;
import com.ensta.interfacegraphique.model.Salle;

import java.util.List;

public class SalleService {
    private static SalleService instance;
    SalleDao SalleDao;

    private SalleService(){
        SalleDao = SalleDao.getInstance();
    }
    public static SalleService getInstance() {
        if(instance==null){
            instance = new SalleService();
        }
        return instance;
    }
    public List<Salle> getList() throws ServiceException {
        try {
            List<Salle> salles = SalleDao.getList();
            return salles;
        } catch (DaoException e) {
            throw new ServiceException("Select salle query failed");
        }
    }

    public Salle getById(String id) throws ServiceException{
        try{
            if(id==null)throw new ServiceException("Select id is null");
            Salle salle = SalleDao.getById(id);
            return salle;
        }catch (DaoException e){
            throw new ServiceException("Select salle by id failed");
        }
    }
    public List<Salle> getByNameorId(String nameOrId) throws ServiceException{
        try{
            if(nameOrId==null)throw new ServiceException("name/id is null");
            List<Salle> salleList = SalleDao.getByNameorId(nameOrId);
            return salleList;
        }catch (DaoException e){
            throw new ServiceException("Select salle by name/id failed");
        }
    }
    public int create(String nom, int etage, int x, int y) throws ServiceException{
        try{
            if(nom==null)throw new ServiceException("salle name null");
            nom = nom.toUpperCase();
            int id = SalleDao.create(nom,etage,x,y);
            return id;
        }catch (DaoException e){
            throw new ServiceException("creating salle failed - service");
        }
    }
    public void update(Salle salle) throws ServiceException{
        try{
            SalleDao.update(salle);
        }catch (DaoException e){
            throw new ServiceException("update salle failed");
        }
    }
    public void delete(String id) throws ServiceException{
        try{
            if(id==null)throw new ServiceException("salle id is null");
            SalleDao.delete(id);
        }catch (DaoException e){
            throw new ServiceException("delete salle failed");
        }
    }
    public int count() throws ServiceException{
        try{
            int cnt = SalleDao.count();
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("salle count failed");
        }
    }

}
