package it.develhope.social.exceptions;

public class PostException extends Exception{

    public PostException() {
        super("Errore sui post");
    }
    public PostException(String message) {
        super(message);
    }
}
