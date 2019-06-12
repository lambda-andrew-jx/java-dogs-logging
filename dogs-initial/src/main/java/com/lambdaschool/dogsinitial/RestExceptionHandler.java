package com.lambdaschool.dogsinitial;

import com.lambdaschool.dogsinitial.errors.DogNotFound;
import com.lambdaschool.dogsinitial.errors.ErrorResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DogNotFound.class)
    public ResponseEntity<?> handleDogNotFound(DogNotFound notFound, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponseModel.builder()
                .status(HttpStatus.NOT_FOUND)
                .title(notFound.getMessage())
                .detail("Dog Not Found")
                .developerMessage("Handler not found")
                .build(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return new ResponseEntity<>(ErrorResponseModel.builder()
                .status(HttpStatus.NOT_FOUND)
                .title(ex.getRequestURL())
                .detail(request.getDescription(true))
                .developerMessage("Handler not found")
                .build(), null, HttpStatus.NOT_FOUND);
    }
}
