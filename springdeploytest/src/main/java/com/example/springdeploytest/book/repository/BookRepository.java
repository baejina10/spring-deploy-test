package com.example.springdeploytest.book.repository;

import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.book.service.response.ListBookResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAccountId(Long accountId);
}
