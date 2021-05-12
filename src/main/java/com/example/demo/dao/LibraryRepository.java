package com.example.demo.dao;

import com.example.demo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String>, LibrarayRepositoryCustom{
}
