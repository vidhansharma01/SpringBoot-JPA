package com.example.demo;

import com.example.demo.entity.Library;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class testIT {
    @Test
    public void getAuthorNameTest() throws JSONException {
        String expected= "[\r\n" +
                "    {\r\n" +
                "        \"book_name\": \"Cypress\",\r\n" +
                "        \"id\": \"abcd4\",\r\n" +
                "        \"isbn\": \"abcd\",\r\n" +
                "        \"aisle\": 4,\r\n" +
                "        \"author\": \"Rahul\"\r\n" +
                "    },\r\n" +
                "    {\r\n" +
                "        \"book_name\": \"Devops\",\r\n" +
                "        \"id\": \"fdsefr343\",\r\n" +
                "        \"isbn\": \"fdsefr3\",\r\n" +
                "        \"aisle\": 43,\r\n" +
                "        \"author\": \"Rahul\"\r\n" +
                "    }\r\n" +
                "]";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:8083/getBooks/author?authorname=Rahul", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addBookIntegrationTest()
    {
        TestRestTemplate restTemplate =new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Library> request = new HttpEntity<>(buildLibrary(),headers);
        ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:8083/addBook", request, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }
    public Library buildLibrary() {
        Library lib =new Library();
        lib.setAisle(32);
        lib.setBook_name("Spring");
        lib.setIsbn("sfes");
        lib.setAuthor("Rahul Shetty");
        lib.setId("sfes32");
        return lib;

    }
}
