package com.example.demo.service;

import com.example.demo.dao.LibraryRepository;
import com.example.demo.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn, int aisle){
        return isbn+aisle;
    }
    public boolean checkBookAlreadyExist(String id){
        Optional<Library> lib = libraryRepository.findById(id);
        if(lib.isPresent())
            return true;
        else
            return false;
    }
    public Library getBookById(String id){
        return libraryRepository.findById(id).get();
    }
}
