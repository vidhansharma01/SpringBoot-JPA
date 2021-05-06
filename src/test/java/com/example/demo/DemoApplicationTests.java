package com.example.demo;

import com.example.demo.controller.BookController;
import com.example.demo.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    BookController bookController;

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
        //bookController.addBook()
    }
}
