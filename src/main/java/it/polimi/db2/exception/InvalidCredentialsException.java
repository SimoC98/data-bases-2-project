package it.polimi.db2.exception;

public class InvalidCredentialsException extends Exception{

    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
