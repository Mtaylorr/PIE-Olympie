package com.ensta.interfacegraphique.service;

import com.ensta.interfacegraphique.dao.PersonnelDao;
import com.ensta.interfacegraphique.exception.DaoException;
import com.ensta.interfacegraphique.exception.ServiceException;
import com.ensta.interfacegraphique.model.Personnel;

import java.util.List;

public class PersonnelService {
    private static PersonnelService instance;
    PersonnelDao personnelDao;

    private PersonnelService(){
        personnelDao = PersonnelDao.getInstance();
    }
    public static PersonnelService getInstance() {
        if(instance==null){
            instance = new PersonnelService();
        }
        return instance;
    }
    public List<Personnel> getList() throws ServiceException {
        try {
            List<Personnel> personnels = personnelDao.getList();
            return personnels;
        } catch (DaoException e) {
            throw new ServiceException("Select personnels query failed");
        }
    }

    public Personnel getById(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("Select id negative");
            Personnel personnel = personnelDao.getById(id);
            return personnel;
        }catch (DaoException e){
            throw new ServiceException("Select personnel by id failed");
        }
    }
    public List<Personnel> getByString(String name) throws ServiceException{
        try{
            if(name==null)throw new ServiceException("name is null");
            List<Personnel> personnelList = personnelDao.getByString(name);
            return personnelList;
        }catch (DaoException e){
            throw new ServiceException("Select personnel by name failed");
        }
    }
    public int create(String nom, String prenom, String salle) throws ServiceException{
        try{
            if(nom==null)throw new ServiceException("personnel lastname null");
            if(prenom==null)throw new ServiceException("personnel name null");
            if(salle==null)throw new ServiceException("salle id null");
            nom = nom.toUpperCase();
            int id = personnelDao.create(nom,prenom,salle);
            return id;
        }catch (DaoException e){
            throw new ServiceException("creating personnel failed - service");
        }
    }
    public void update(Personnel personnel) throws ServiceException{
        try{
            if(personnel.getNom()==null)throw new ServiceException("personnel lastname null");
            if(personnel.getPrenom()==null)throw new ServiceException("personnel name null");
            personnel.setNom(personnel.getNom().toUpperCase());
            personnelDao.update(personnel);
        }catch (DaoException e){
            throw new ServiceException("update personnel failed");
        }
    }
    public void delete(int id) throws ServiceException{
        try{
            if(id<0)throw new ServiceException("personnel id negative");
            personnelDao.delete(id);
        }catch (DaoException e){
            throw new ServiceException("delete personnel failed");
        }
    }
    public int count() throws ServiceException{
        try{
            int cnt = personnelDao.count();
            return cnt;
        }catch (DaoException e){
            throw new ServiceException("personnel count failed");
        }
    }

}
