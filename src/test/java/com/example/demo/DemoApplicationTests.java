package com.example.demo;

import com.example.demo.controller.BookController;
import com.example.demo.dao.LibraryRepository;
import com.example.demo.entity.Library;
import com.example.demo.entity.ResponseBook;
import com.example.demo.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    BookController bookController;

    @MockBean
    LibraryRepository libraryRepository;
    @MockBean
    LibraryService libraryService;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBook(){
        LibraryService libraryService = new LibraryService();
        String val = libraryService.buildId("MAN", 13);
        assertEquals(val, "MAN13");
    }
    //Mockito
    @Test
    public void addBookTest(){
	    Library lib = sendLibrary();
        when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);

        ResponseEntity responseEntity = bookController.addBook(lib);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        ResponseBook responseBook = (ResponseBook) responseEntity.getBody();
        assertEquals(responseBook.getId(), lib.getId());
        assertEquals(responseBook.getMsg(), "Book is successfully added");
    }

    public Library sendLibrary(){
	    Library library = new Library();
	    library.setBook_name("Microservices");
	    library.setAuthor("Gene Teglovic");
	    library.setAisle(3232);
	    library.setId("4343dff");
	    library.setIsbn("4343vvfv");
	    return library;
    }
}
