package com.iprody.paymentservice.aop;

import com.iprody.paymentservice.aop.exception.ResourceNotFoundException;
import com.iprody.paymentservice.aop.exception.ResourceProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * Class for exceptions handling.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Constant response code BAD_REQUEST.
     */
    private static final int RESPONSE_CODE_BAD_REQUEST = 400;

    /**
     * Constant response code NOT_FOUND.
     */
    private static final int RESPONSE_CODE_NOT_FOUND = 404;

    /**
     * Constant response code INTERNAL_SERVER_ERROR.
     */
    private static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;

    /**
     * Constant response string message.
     */
    private static final String VALIDATION_FAILED_FOR_ARGUMENT = "Validation failed for argument";

    /**
     * Exception handler for Exception.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleException(final Exception exception) {
        return new ExceptionResponse(RESPONSE_CODE_INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                List.of(exception.getMessage()));
    }

    /**
     * Exception handler for HandlerMethodValidationException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleHandlerMethodValidationException(
            final HandlerMethodValidationException exception) {

        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                "Bad request",
                List.of(exception.getMessage(), exception.getReason()));
    }

    /**
     * Exception handler for HttpMessageNotReadableException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException exception) {

        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                "Required request body is missing",
                null);
    }

    /**
     * Exception handler for MethodArgumentNotValidException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleResourceNotFoundException(
            final MethodArgumentNotValidException exception) {

        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                VALIDATION_FAILED_FOR_ARGUMENT,
                exception.getBindingResult().getAllErrors()
                        .stream()
                        .map(e -> e.toString())
                        .toList());
    }

    /**
     * Exception handler for ResourceNotFoundException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleResourceNotFoundException(final ResourceNotFoundException exception) {
        return new ExceptionResponse(RESPONSE_CODE_NOT_FOUND,
                exception.getMessage(),
                null);
    }

    /**
     * Exception handler for ResourceProcessingException.class.
     *
     * @param exc     The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(ResourceProcessingException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleResourceProcessingException(final ResourceProcessingException exc) {
        return new ExceptionResponse(RESPONSE_CODE_INTERNAL_SERVER_ERROR,
                exc.getMessage(),
                null);
    }

    /**
     * Exception handler for ConstraintViolationException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleConstraintViolationException(
            final ConstraintViolationException exception) {
        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                VALIDATION_FAILED_FOR_ARGUMENT,
                List.of(exception.getConstraintViolations().toString()));
    }

    /**
     * Exception handler for NoSuchElementException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleNoSuchElementException(final NoSuchElementException exception) {
        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                "Could not find element",
                List.of(exception.getMessage()));
    }

    /**
     * Exception handler for DataIntegrityViolationException.class.
     *
     * @param exception The exception object.
     * @return ExceptionResponse class with details.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleDataIntegrityViolationException(
            final DataIntegrityViolationException exception) {
        return new ExceptionResponse(RESPONSE_CODE_BAD_REQUEST,
                "Duplicate key constraint",
                List.of(exception.getMostSpecificCause().getMessage()));
    }
}
