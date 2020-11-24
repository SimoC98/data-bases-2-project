package it.polimi.db2.exception;

public class ProductAlreadyExistingException extends Exception{
    public ProductAlreadyExistingException() {}

    public ProductAlreadyExistingException(String message) {
        super(message);
    }
}
