package com.spring.spring_6_rest_mvc.exception_handling;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFound(@Autowired HttpServletRequest request){
        // just to make sure it is getting called
        log.info("ResourceNotFoundException handled, culprit request: "
                + request.getMethod() + ": " + request.getRequestURI());
        return ResponseEntity.notFound().build();
    }


}
