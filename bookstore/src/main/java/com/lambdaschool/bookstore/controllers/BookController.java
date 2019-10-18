package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.AuthorService;
import com.lambdaschool.bookstore.services.BookService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/data/books")
public class BookController
{
    @Autowired
    private BookService bookService;
    private AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @ApiOperation(value = "returns all Books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(HttpServletRequest request, @PageableDefault(page = 0, size = 3) Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    @ApiOperation(value = "Edits Book associated with Sent Id.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book Edited Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Book Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Editing Book", response = ErrorDetail.class
            )})
    @PutMapping(value = "/{bookid}")
    public ResponseEntity<?> updateBook( HttpServletRequest request, @RequestBody Book updateBook, @PathVariable long bookid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a Book by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Book", response = ErrorDetail.class
            )})
    @DeleteMapping("/{bookid}")
    public ResponseEntity<?> deleteBookById( HttpServletRequest request, @PathVariable long bookid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Assigns a Book to an Author.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book/ Author Combo Added Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Book or Author Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Adding Book/Author Combo", response = ErrorDetail.class
            )})
    @PostMapping("/{bookid}/authors/{authorid}")
    public ResponseEntity<?> postWroteByIds(HttpServletRequest request, @PathVariable long bookid,
                                            @PathVariable long authorid)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        authorService.addWrote(bookid, authorid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
