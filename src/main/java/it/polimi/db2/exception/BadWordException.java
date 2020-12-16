package it.polimi.db2.exception;

public class BadWordException extends Exception{
    public BadWordException() {
    }

    public BadWordException(String message) {
        super(message);
    }
}
