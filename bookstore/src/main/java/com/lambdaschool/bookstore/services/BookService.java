package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService
{
    List<Book> findAll(Pageable pageable);

    Book findBookById(long id);

    void delete(long id);

    Book save (Book book);

    Book update(Book book, long id);
}
