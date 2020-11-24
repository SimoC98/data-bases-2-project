package it.polimi.db2.exception;

public class CompilationAlreadyExistingException extends Exception{
    public CompilationAlreadyExistingException() {
    }

    public CompilationAlreadyExistingException(String message) {
        super(message);
    }
}
