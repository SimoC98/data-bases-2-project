package it.polimi.db2.exception;

public class InvalidRegistrationException extends Exception{

    public InvalidRegistrationException() {
    }

    public InvalidRegistrationException(String message) {
        super(message);
    }
}
