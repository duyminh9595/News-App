package com.dongok.hello.dao;

import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.entity.NewsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "newstype",collectionResourceRel = "newstype")
@CrossOrigin
public interface NewsTypeRepository extends JpaRepository<NewsType,Long> {
    @Query("select p from NewsType  p   order by p.name asc ")
    Page<NewsType> findAllTypes(Pageable pageable);

    @Query("select p from NewsType  p  where p.id=?1")
    NewsType findData(Long id);

    @Query("select p from NewsType  p   order by p.name asc ")
    List<NewsType> findListTypes();

}
