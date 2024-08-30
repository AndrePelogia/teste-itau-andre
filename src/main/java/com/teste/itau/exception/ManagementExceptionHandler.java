package com.teste.itau.exception;

import com.teste.itau.dto.response.ErrorApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@ControllerAdvice
public class ManagementExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponseDTO> handleContaExistenteException(CustomApiException ex) {
        List<Map<String, String>> listMap = new ArrayList<>();
        ErrorApiResponseDTO errors = new ErrorApiResponseDTO();
        errors.setMensagem(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponseDTO> handleContaExistenteException(NotFoundException ex) {
        List<Map<String, String>> listMap = new ArrayList<>();
        ErrorApiResponseDTO errors = new ErrorApiResponseDTO();
        errors.setMensagem(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(BDException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleContaExistenteException(BDException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorApiResponseDTO errors = new ErrorApiResponseDTO();
        errors.setMensagem(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
