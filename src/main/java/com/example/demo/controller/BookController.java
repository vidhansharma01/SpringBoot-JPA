package com.example.demo.controller;

import com.example.demo.dao.LibraryRepository;
import com.example.demo.entity.Library;
import com.example.demo.entity.ResponseBook;
import com.example.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BookController {
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    ResponseBook responseBook;

    @Autowired
    LibraryService libraryService;

    @PostMapping("/addBook")
    public ResponseEntity<ResponseBook> addBook(@RequestBody Library library){
        String id = libraryService.buildId(library.getIsbn(), library.getAisle());
        if(!libraryService.checkBookAlreadyExist(id)) {
            library.setId(id);
            libraryRepository.save(library);

            //Providing response
            responseBook.setMsg("Book is successfully added");
            responseBook.setId(id);
            ResponseEntity<ResponseBook> responseEntity = new ResponseEntity<>(responseBook, HttpStatus.CREATED);
            return responseEntity;
        }else{
            responseBook.setMsg("Book already exist");
            responseBook.setId(id);
            return new ResponseEntity<ResponseBook>(responseBook, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getBooks/{id}")
    public Library getBookById(@PathVariable(value = "id")String id){
        try{
            Library lib = libraryRepository.findById(id).get();
            return lib;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
