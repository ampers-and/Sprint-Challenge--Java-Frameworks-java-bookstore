package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>
{
    Book findByBookname(String bookname);

    List<Book> findByBooknameContainingIgnoreCase(String name,
                                                  Pageable pageable);

    @Query(value = "SELECT COUNT(*) as count FROM wrote WHERE authorid = :authorid AND bookid = :bookid",
            nativeQuery = true)
    com.lambdaschool.starthere.view.JustTheCount checkWrote(long bookid,
                                                            long authorid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Wrote WHERE bookid = :bookid AND authorid = :authorid")
    void deleteWrote(long bookid,
                     long authorid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Wrote(bookid, authorid) VALUES (:bookid, :authorid)",
            nativeQuery = true)
    void insertWrote(long bookid,
                     long authorid);
}
