package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService
{
    List<Author> findAll(Pageable pageable);

    Author findAuthorById(long id);

    void delete(long id);

    Author save(Author author);

    Author update(Author author, long id);

    void addWrote(long bookid,
                     long authorid);
}
