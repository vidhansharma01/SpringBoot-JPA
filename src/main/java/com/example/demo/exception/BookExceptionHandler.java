package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = {BookException.class})
    public ResponseEntity<BookExceptionSchema> handleGetBookByIdException(BookException exception){
        BookExceptionSchema bookExceptionSchema = new BookExceptionSchema();
        bookExceptionSchema.setId("23");
        bookExceptionSchema.setMessage("Sunderland - Till I live");
        return new ResponseEntity<>(bookExceptionSchema, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<BookExceptionSchema> handleGetBookException(Exception ex){
        BookExceptionSchema bookExceptionSchema = new BookExceptionSchema();
        bookExceptionSchema.setId("23");
        bookExceptionSchema.setMessage("Sunderland");
        return new ResponseEntity<>(bookExceptionSchema, HttpStatus.NOT_FOUND);
    }
}
