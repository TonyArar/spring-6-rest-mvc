package com.spring.spring_6_rest_mvc.exception_handling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

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
        // if we want we can log them as well
        // log.warn("Bad request details: " + objectMapper.writeValueAsString(fieldErrors));
        return ResponseEntity.badRequest().body(fieldErrors);
    }


    // if provided type in the request is not the required type
    // for example: if instead of a UUID, the client provides an int in the URI
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatchException(@Autowired MethodArgumentTypeMismatchException exception){
        return ResponseEntity.badRequest().body("Provided wrong type, required type: " + exception.getRequiredType().toString());
    }


}
