package com.example.newsservicesecurity.exception;

import com.example.newsservicesecurity.api.v1.response.ErrorRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRs> handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.badRequest().body(makeErrors("AccessDeniedException", e));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorRs> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(makeErrors("EntityNotFoundException", e));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorRs> handleValidationExceptions(
            MissingServletRequestParameterException ex) {
        log.info("MissingServletRequestParameterException " + ex);
        return ResponseEntity.badRequest().body(makeErrors("MissingServletRequestParameterException", ex));
    }

    @ExceptionHandler(AlreadySuchNameException.class)
    public ResponseEntity<ErrorRs> handleAlreadySuchNameException(
            AlreadySuchNameException ex) {
        log.info("AlreadySuchNameException " + ex);
        return ResponseEntity.badRequest().body(makeErrors("AlreadySuchNameException", ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("MethodArgumentNotValidException " + fieldName, errorMessage);
        });
        return errors;
    }

    private ErrorRs makeErrors(String error, Exception e){
        ErrorRs errorRs = new ErrorRs();
        errorRs.setError(error);
        errorRs.setErrorDescription(e.getMessage());
        errorRs.setTimestamp(System.currentTimeMillis());

        return errorRs;
    }

}
