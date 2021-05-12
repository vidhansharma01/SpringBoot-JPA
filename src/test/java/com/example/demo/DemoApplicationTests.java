package com.example.demo;

import com.example.demo.controller.BookController;
import com.example.demo.dao.LibraryRepository;
import com.example.demo.entity.Library;
import com.example.demo.entity.ResponseBook;
import com.example.demo.service.LibraryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    BookController bookController;

    @MockBean
    LibraryRepository libraryRepository;
    @MockBean
    LibraryService libraryService;

    @Autowired
    MockMvc mockMvc;

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

    //MockMvc
    @Test
    public void addBookControllerTest() throws Exception {
        Library lib = sendLibrary();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(lib);

        when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
        this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(lib.getId()));
        
    }

    @Test
    public void getBookByAuthorTest() throws Exception {
        List<Library> list = new ArrayList<>();
        list.add(sendLibrary());
        when(libraryRepository.findAllByAuthor(any())).thenReturn(list);
        this.mockMvc.perform(get("/getBooks/author").param("authorname", "Gene"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("4343vvfv3232"));
    }

    @Test
    public void updateBookTest() throws Exception {
	    Library library = sendLibrary();
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonString = objectMapper.writeValueAsString(updateLibrary());
	    when(libraryService.getBookById(any())).thenReturn(sendLibrary());
	    this.mockMvc.perform(put("/updateBook/" + library.getId())
        .contentType(MediaType.APPLICATION_JSON).content(jsonString)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookControllerTest() throws Exception{
        when(libraryService.getBookById(any())).thenReturn(sendLibrary());
        doNothing().when(libraryRepository).delete(sendLibrary());
        this.mockMvc.perform(delete("/deleteBook").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" : \"sfe3b\"}")).andDo(print())
                .andExpect(status().isCreated()).andExpect(content().string("Book deleted"));
    }

    public Library sendLibrary(){
	    Library library = new Library();
	    library.setBook_name("Microservices");
	    library.setAuthor("Gene");
	    library.setAisle(3232);
	    library.setId("4343vvfv3232");
	    library.setIsbn("4343vvfv");
	    return library;
    }
    public Library updateLibrary()
    {
        Library lib =new Library();
        lib.setAisle(322);
        lib.setBook_name("Boot");
        lib.setIsbn("rain");
        lib.setAuthor("Shetty");
        lib.setId("rain322");
        return lib;

    }
}
