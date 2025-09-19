package ru.practicum.exception;

public class IncorrectValueException extends RuntimeException {

    public IncorrectValueException(String message) {
        super(message);
    }
}