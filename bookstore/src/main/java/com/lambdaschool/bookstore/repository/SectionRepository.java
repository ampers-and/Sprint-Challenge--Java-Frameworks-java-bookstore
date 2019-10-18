package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Section;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SectionRepository extends PagingAndSortingRepository<Section, Long>
{
}
