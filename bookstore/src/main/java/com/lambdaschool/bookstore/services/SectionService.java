package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Section;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SectionService
{
    List<Section> findAll(Pageable pageable);

    Section findSectionById(long id);

    void delete(long id);

    Section save(Section section);

    Section update(Section section, long id);
}
