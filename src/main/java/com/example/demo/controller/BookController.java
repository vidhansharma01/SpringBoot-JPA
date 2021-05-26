package com.example.demo.controller;

import com.example.demo.dao.LibraryRepository;
import com.example.demo.entity.Library;
import com.example.demo.exception.BookException;
import com.example.demo.model.ResponseBook;
import com.example.demo.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    ResponseBook responseBook;

    @Autowired
    LibraryService libraryService;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

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
    public void getBookById(@PathVariable(value = "id")String id) throws Exception {

            Library lib = libraryRepository.findById(id).get();
            if (lib == null)
                throw new BookException("Nope");
    }

    @GetMapping("getBooks/author")
    public List<Library> getBookByAuthorName(@RequestParam(value = "authorname") String authorName){
        return libraryRepository.findAllByAuthor(authorName);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable(value = "id") String id,
                                              @RequestBody Library library){
        Library existingBook = libraryService.getBookById(id);
        existingBook.setAisle(library.getAisle());
        existingBook.setAuthor(library.getAuthor());
        existingBook.setBook_name(library.getBook_name());
        libraryRepository.save(existingBook);
        return new ResponseEntity<Library>(existingBook, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBookById(@RequestBody Library library){
        logger.info("Book deleted");
        Library lib = libraryService.getBookById(library.getId());
        libraryRepository.delete(lib);
        return new ResponseEntity<>("Book deleted", HttpStatus.CREATED);
    }
}
