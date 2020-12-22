package org.apiproject.boot.demo;

public class DataCannotCreateException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /* public DataCannotCreateException() {
    } */
    public DataCannotCreateException(){
        super("Could not created the data");
    }
}
