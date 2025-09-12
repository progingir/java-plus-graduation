package ru.practicum.exception.handler;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.practicum.exception.*;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String reasonMessage;
        String errorMessage;
        if (e.getMessage().contains("users_email_key")) {
            reasonMessage = "Creating user with already existing email";
            errorMessage = "Email already exists";
        } else if (e.getMessage().contains("categories_name_key")) {
            reasonMessage = "Creating category with already existing name";
            errorMessage = "Category already exists";
        } else {
            reasonMessage = "Integrity violation";
            errorMessage = e.getMessage();
        }
        log.error("Conflict: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(errorMessage))
                .message(errorMessage)
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEventIdIncorrectException(EventIdIncorrectException e) {
        String reasonMessage = "Integrity violation";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyPublishedException(AlreadyPublishedException e) {
        String reasonMessage = "Event already published";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePublicationException(PublicationException e) {
        String reasonMessage = "Publication failed";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String reasonMessage = "Body of the request is not readable";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler({
            ValidationException.class,
            UpdateStartDateException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class,
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            ServiceUnavailableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(Exception e) {
        String reasonMessage;
        if (e instanceof ValidationException) {
            reasonMessage = "Validation failed";
        } else if (e instanceof UpdateStartDateException) {
            reasonMessage = "Update start date failed";
        } else if (e instanceof MethodArgumentNotValidException) {
            reasonMessage = "Method argument not valid";
        } else if (e instanceof HandlerMethodValidationException) {
            reasonMessage = "Handler method not valid";
        } else if (e instanceof IllegalArgumentException) {
            reasonMessage = "Not valid request";
        } else if (e instanceof MissingServletRequestParameterException) {
            reasonMessage = "Missing request parameter";
        } else {
            reasonMessage = "Service unavailable";
        }
        log.error("BAD_REQUEST: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.BAD_REQUEST.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        String reasonMessage = "Entity not found";
        log.error("NOT_FOUND: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason("Entity not found")
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotPublishedEventRequestException(NotPublishedEventRequestException e) {
        String reasonMessage = "Request to not published event";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestLimitException(RequestLimitException e) {
        String reasonMessage = "Limit request";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRequestException(DuplicateRequestException e) {
        String reasonMessage = "Duplicate request";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInitiatorRequestException(InitiatorRequestException e) {
        String reasonMessage = "Initiator request";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGetPublicEventException(GetPublicEventException e) {
        String reasonMessage = "Get event exception";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(RuntimeException e) {
        String reasonMessage = "Unknown error";
        log.error("INTERNAL_SERVER_ERROR: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleTooManyRequestsException(TooManyRequestsException e) {
        String reasonMessage = "Too many requests";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyConfirmedException(AlreadyConfirmedException e) {
        String reasonMessage = "Request already confirmed";
        log.error("CONFLICT: {}", reasonMessage, e);
        return ErrorResponse.builder()
                .errors(List.of(e.getMessage()))
                .message(e.getMessage())
                .reason(reasonMessage)
                .status(HttpStatus.CONFLICT.toString())
                .build();
    }
}