package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "bookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRepository bookrepos;

    @Override
    public List<Book> findAll(Pageable pageable)
    {
        List<Book> list = new ArrayList<>();
        bookrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book findBookById(long id)
    {
        return bookrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (bookrepos.findById(id).isPresent())
        {
            bookrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Book save(Book book)
    {
        Book newBook = new Book();

        newBook.setBookid(book.getBookid());
        newBook.setBooktitle(book.getBooktitle());
        newBook.setCopy(book.getCopy());
        newBook.setISBN(book.getISBN());

        return bookrepos.save(newBook);
    }

    @Override
    public Book update(Book book, long id)
    {
        Book currentBook = bookrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if(book.getBookid() != currentBook.getBookid())
        {
            currentBook.setBookid(book.getBookid());
        }
        if(book.getBooktitle() != null)
        {
            currentBook.setBooktitle(book.getBooktitle());
        }
        if(book.getCopy() != currentBook.getCopy())
        {
            currentBook.setCopy(book.getCopy());
        }
        if(book.getISBN() != null)
        {
            currentBook.setISBN(book.getISBN());
        }

        return bookrepos.save(currentBook);
    }
}
