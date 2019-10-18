package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Useremail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UseremailRepository extends CrudRepository<Useremail, Long>
{
    List<Useremail> findAllByUser_Username(String name);
}
