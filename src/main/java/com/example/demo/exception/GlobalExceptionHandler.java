package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {
        log.warn("Accès non autorisé: {}", ex.getMessage());
        return new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("Accès refusé: {}", ex.getMessage());
        return new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "Vous n'avez pas les droits nécessaires pour accéder à cette ressource",
            request.getDescription(false)
        );
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(Exception ex, WebRequest request) {
        log.warn("Échec de l'authentification: {}", ex.getMessage());
        return new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Identifiants invalides ou compte inexistant",
            request.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Erreur de validation: {}", errors);
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });
        log.warn("Violation de contrainte: {}", errors);
        return errors;
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, 
                      HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(Exception ex, WebRequest request) {
        log.warn("Requête invalide: {}", ex.getMessage());
        return new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Requête invalide: " + ex.getMessage(),
            request.getDescription(false)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Violation d'intégrité des données: {}", ex.getMessage(), ex);
        return new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Erreur d'intégrité des données. Vérifiez les contraintes d'unicité.",
            request.getDescription(false)
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        log.warn("Méthode non supportée: {}", ex.getMethod());
        return new ErrorResponse(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            "Méthode non autorisée pour cette ressource",
            request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllUncaughtException(Exception ex, WebRequest request) {
        log.error("Erreur inattendue: {}", ex.getMessage(), ex);
        return new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Une erreur inattendue s'est produite. Veuillez réessayer plus tard.",
            request.getDescription(false)
        );
    }

    // Classe interne pour la structure de réponse d'erreur standard
    public record ErrorResponse(
        int status,
        String message,
        String path
    ) {}
}
