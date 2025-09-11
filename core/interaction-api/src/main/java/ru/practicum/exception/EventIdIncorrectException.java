package ru.practicum.exception;

public class EventIdIncorrectException extends RuntimeException {
    public EventIdIncorrectException(String message) {
        super(message);
    }
}