package org.example.exception;

public class PersonDAOException extends RuntimeException {
    public PersonDAOException(String message) {
        super(message);
    }

    public PersonDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
