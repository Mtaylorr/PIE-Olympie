package com.ensta.interfacegraphique.exception;

public class DaoException extends Exception{
    public DaoException() {
        super();
    }
    public DaoException(String s) {
        super();
        System.out.println(s);
    }
}
