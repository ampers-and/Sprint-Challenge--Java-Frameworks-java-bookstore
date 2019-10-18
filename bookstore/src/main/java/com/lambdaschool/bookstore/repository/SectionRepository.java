package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Section;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SectionRepository extends PagingAndSortingRepository<Section, Long>
{
}
