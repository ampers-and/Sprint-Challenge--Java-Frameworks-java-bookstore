package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.AuthorService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/data/authors")
public class AuthorController
{
    @Autowired
    private AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @ApiOperation(value = "returns all Authors", response = Author.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> listAllAuthors(HttpServletRequest request, @PageableDefault(page = 0, size = 3) Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        List<Author> myAuthors = authorService.findAll(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }

    //STRETCH
    @ApiOperation(value = "Edits Author associated with Sent Id.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author Edited Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Author Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Editing Author", response = ErrorDetail.class
            )})
    @PutMapping(value = "/{authorid}")
    public ResponseEntity<?> updateAuthor(HttpServletRequest request,
                                        @RequestBody Author updateAuthor,
                                        @PathVariable long authorid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        authorService.update(updateAuthor, authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a Author by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Author", response = ErrorDetail.class
            )})
    @DeleteMapping("/{authorid}")
    public ResponseEntity<?> deleteAuthorById(HttpServletRequest request, @PathVariable long authorid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        authorService.delete(authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a Author by Id.", response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author Found", response = Author.class),
            @ApiResponse(code = 404, message = "Author Not Found", response = ErrorDetail.class
            )})
    @GetMapping(value = "/author/{authorId}",
            produces = {"application/json"})
    public ResponseEntity<?> getAuthorById(HttpServletRequest request, @PathVariable Long authorId)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        Author r = authorService.findAuthorById(authorId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds a New Author.", notes = "The newly created author id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New Author Added Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error Adding New Author", response = ErrorDetail.class
            )})
    @PostMapping(value = "/author",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewAuthor( HttpServletRequest request, @Valid @RequestBody Author newAuthor) throws URISyntaxException
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        newAuthor = authorService.save(newAuthor);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAuthorURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{authorid}").buildAndExpand(newAuthor.getAuthorid()).toUri();
        responseHeaders.setLocation(newAuthorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
