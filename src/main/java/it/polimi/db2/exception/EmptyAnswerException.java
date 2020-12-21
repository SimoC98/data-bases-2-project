package it.polimi.db2.exception;

public class EmptyAnswerException extends Exception{

    public EmptyAnswerException() {
    }

    public EmptyAnswerException(String message) {
        super(message);
    }
}
