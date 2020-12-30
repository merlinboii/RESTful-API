package org.apiproject.boot.demo.exception;

public class DataNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataNotFoundException() {

    }

    public DataNotFoundException(long id){
        super("Could not find data id :: "+ id);
    }
    
}
