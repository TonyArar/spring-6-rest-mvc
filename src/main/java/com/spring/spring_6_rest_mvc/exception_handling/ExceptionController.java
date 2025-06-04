package com.spring.spring_6_rest_mvc.exception_handling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(@Autowired HttpServletRequest request){
        // just to make sure it is getting called
        log.warn("ResourceNotFoundException handled, culprit request: "
                + request.getMethod() + ": " + request.getRequestURI());
        return ResponseEntity.notFound().build();
    }

    // this is the Exception thrown by Jakarta Validation
    // if exception handling is not defined by us, such as here,
    // then it is handled by the DefaultHandlerExceptionResolver
    // of spring, whose http response is not very informative
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleDataValidationException(
            @Autowired HttpServletRequest request,
            @Autowired MethodArgumentNotValidException exception)
            throws JsonProcessingException {
        log.warn("MethodArgumentNotValidException handled, culprit request: "
                + request.getMethod() + ": " + request.getRequestURI());
        // get field errors from exception to return them to client in the response body
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        // more informative to client
        List responseBody = fieldErrors.stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return errorMap;
        }).toList();
        // if we want we can log them as well
        // log.warn("Bad request details: " + objectMapper.writeValueAsString(fieldErrors));
        return ResponseEntity.badRequest().body(responseBody);
    }

    // if provided type in the request is not the required type
    // for example: if instead of a UUID, the client provides an int in the URI
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatchException(@Autowired MethodArgumentTypeMismatchException exception){
        return ResponseEntity.badRequest().body("Provided wrong type, required type: " + exception.getRequiredType().toString());
    }

    // just for the heck of it
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(@Autowired ConstraintViolationException exception) {
        List errors = exception.getConstraintViolations().stream().map(
                constraintViolation -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getMessage());
                    return errorMap;
                }
        ).toList();
        // log.warn("ConstraintViolationException, errors: " + objectMapper.writeValueAsString(errors));
        return ResponseEntity.badRequest().body(errors);
    }

    // just for the heck of it
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(@Autowired DataIntegrityViolationException exception){
        // log.warn("DataIntegrityViolationException, errors: " + exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
