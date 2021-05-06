package com.example.demo.dao;

import com.example.demo.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LibrarayRepositoryImpl implements LibrarayRepositoryCustom {

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public List<Library> findAllByAuthor(String authorName) {
        List<Library> booksWithAuthor = new ArrayList<>();

        List<Library> books = libraryRepository.findAll();
        for(Library book: books){
            if(book.getAuthor().equalsIgnoreCase(authorName))
                booksWithAuthor.add(book);
        }
        return booksWithAuthor;
    }
}
