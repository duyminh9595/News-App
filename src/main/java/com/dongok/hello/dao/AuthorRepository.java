package com.dongok.hello.dao;

import com.dongok.hello.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;



@RepositoryRestResource(path = "author",collectionResourceRel = "author")
@CrossOrigin
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("select p from Author  p  where p.email=?1")
    Author findData(String email);
    @Query("select p from Author  p  where p.id=?1")
    Author findDataById(Long id);

}
