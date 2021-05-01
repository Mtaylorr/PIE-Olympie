package com.ensta.interfacegraphique.exception;

public class ServiceException extends Exception{
    public ServiceException() {
        super();
    }
    public ServiceException(String s) {
        super();
        System.out.println(s);
    }
}
