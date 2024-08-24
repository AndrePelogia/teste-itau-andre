package com.teste.itau.exception;

import com.teste.itau.dto.ErrorApiResponseDTO;
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

    @ExceptionHandler(ContaExistenteException.class)
    @ResponseBody
    public ResponseEntity<ErrorApiResponseDTO> handleContaExistenteException(ContaExistenteException ex) {
        List<Map<String, String>> listMap = new ArrayList<>();
        ErrorApiResponseDTO errors = new ErrorApiResponseDTO();
        Map<String, String> mapErros = new HashMap<>();
        mapErros.put("erro",ex.getMessage());
        listMap.add(mapErros);
        errors.setMensagem(listMap);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
        List<Map<String, String>> listMap = new ArrayList<>();
        ErrorApiResponseDTO errors = new ErrorApiResponseDTO();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> mapErros = new HashMap<>();
            mapErros.put("erro",error.getDefaultMessage());
            listMap.add(mapErros);
            errors.setMensagem(listMap);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
