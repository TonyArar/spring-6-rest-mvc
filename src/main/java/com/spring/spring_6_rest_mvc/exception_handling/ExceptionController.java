package com.spring.spring_6_rest_mvc.exception_handling;

import com.spring.spring_6_rest_mvc.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// from docs of @ControllerAdvice: @ControllerAdvice is a
// specialization of @Component for classes that declare
// @ExceptionHandler, @InitBinder, or @ModelAttribute methods
// to be shared across multiple @Controller classes.
// compared to @RestControllerAdvice, this gives us control
// on whether we want to return a response body:
// (not annotated with @ResponseBody at the class level)

// global/shared/centralized exception handling among multiple controllers

// << for full control over exception handling, response body and response status code >>
// << commented out in favour of a simplified version using @ResponseStatus in Exception class>>

// @ControllerAdvice
public class ExceptionController {


    // @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFound(){
        // just to make sure it is getting called
        System.out.println("handleResourceNotFound");
        return ResponseEntity.notFound().build();
    }


}
