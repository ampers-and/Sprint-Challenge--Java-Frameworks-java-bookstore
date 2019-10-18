package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.SectionService;
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
@RequestMapping("/data/sections")
public class SectionController
{
    @Autowired
    private SectionService sectionService;
    private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

    @ApiOperation(value = "returns all Sections", response = Section.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/sections", produces = {"application/json"})
    public ResponseEntity<?> listAllSections(HttpServletRequest request, @PageableDefault(page = 0, size = 3) Pageable pageable)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        List<Section> mySections = sectionService.findAll(pageable);
        return new ResponseEntity<>(mySections, HttpStatus.OK);
    }

    //STRETCH
    @ApiOperation(value = "Edits Section associated with Sent Id.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Section Edited Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Section Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Editing Section", response = ErrorDetail.class
            )})
    @PutMapping(value = "/{sectionid}")
    public ResponseEntity<?> updateSection(HttpServletRequest request,
                                        @RequestBody Section updateSection,
                                        @PathVariable long sectionid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        sectionService.update(updateSection, sectionid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes a Section by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Section Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Section", response = ErrorDetail.class
            )})
    @DeleteMapping("/{sectionid}")
    public ResponseEntity<?> deleteSectionById(HttpServletRequest request, @PathVariable long sectionid)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        sectionService.delete(sectionid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a Section by Id.", response = Section.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Section Found", response = Section.class),
            @ApiResponse(code = 404, message = "Section Not Found", response = ErrorDetail.class
            )})
    @GetMapping(value = "/section/{sectionId}",
            produces = {"application/json"})
    public ResponseEntity<?> getSectionById(HttpServletRequest request, @PathVariable Long sectionId)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        Section r = sectionService.findSectionById(sectionId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds a New Section.", notes = "The newly created section id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New Section Added Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error Adding New Section", response = ErrorDetail.class
            )})
    @PostMapping(value = "/section",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewSection( HttpServletRequest request, @Valid @RequestBody Section newSection) throws URISyntaxException
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        newSection = sectionService.save(newSection);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newSectionURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{sectionid}").buildAndExpand(newSection.getSectionid()).toUri();
        responseHeaders.setLocation(newSectionURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
