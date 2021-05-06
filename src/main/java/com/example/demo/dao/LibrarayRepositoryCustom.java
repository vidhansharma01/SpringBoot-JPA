package com.example.demo.dao;

import com.example.demo.entity.Library;

import java.util.List;

public interface LibrarayRepositoryCustom {
    public List<Library> findAllByAuthor(String authorName);
}
