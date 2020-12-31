package org.apiproject.boot.demo.exception;

public class INTERNAL_SERVER_ERROR extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public INTERNAL_SERVER_ERROR() {
        super("Internal Server Error :: Format is invalid.");
    }
    
}
