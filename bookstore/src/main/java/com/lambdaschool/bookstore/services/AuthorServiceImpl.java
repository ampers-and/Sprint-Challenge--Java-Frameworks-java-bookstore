package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.exceptions.ResourceFoundException;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "authorService")
public class AuthorServiceImpl implements AuthorService
{
    @Autowired
    private AuthorRepository authrepos;
    private BookRepository bookrepos;

    @Override
    public List<Author> findAll(Pageable pageable)
    {
        List<Author> list = new ArrayList<>();
        authrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Author findAuthorById(long id)
    {
        return authrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (authrepos.findById(id).isPresent())
        {
            authrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Author save(Author author)
    {
        Author newAuthor = new Author();

        newAuthor.setFirstname(author.getFirstname());
        newAuthor.setLastname(author.getLastname());

        return authrepos.save(newAuthor);
    }

    @Override
    public Author update(Author author, long id)
    {
        Author currentAuthor = authrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (author.getFirstname() != null)
        {
            currentAuthor.setFirstname(author.getFirstname());
        }
        if (author.getLastname() != null)
        {
            currentAuthor.setLastname(author.getLastname());
        }

        return authrepos.save(currentAuthor);
    }

    @Transactional
    @Override
    public void addWrote(long bookid,
                            long authorid)
    {
        authrepos.findById(authorid)
                .orElseThrow(() -> new ResourceNotFoundException("Author id " + authorid + " not found!"));
        bookrepos.findById(bookid)
                .orElseThrow(() -> new ResourceNotFoundException("Book id " + bookid + " not found!"));

        if (bookrepos.checkWrote(bookid, authorid)
                .getCount() <= 0)
        {
            bookrepos.insertWrote(bookid, authorid);
        } else
        {
            throw new ResourceFoundException("Book and Author Combination Already Exists");
        }
    }
}
