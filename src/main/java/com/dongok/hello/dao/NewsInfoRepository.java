package com.dongok.hello.dao;

import com.dongok.hello.entity.NewsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "news",collectionResourceRel = "news")
@CrossOrigin
public interface NewsInfoRepository extends JpaRepository<NewsInfo,Long> {
    @Query(value = "select p from NewsInfo  p   order by p.Date_Created desc ")
    List<NewsInfo> findNewsAll ();

    @Query("select p from NewsInfo  p where p.Content like CONCAT('%',:content,'%') order by p.Date_Created asc ")
    List<NewsInfo> findByContent(String content);

    @Query("select p from NewsInfo  p where p.type.id=?1 order by p.Date_Created desc ")
    List<NewsInfo> findByTheLoaiId(Long id);

    @Query("select p from NewsInfo  p   order by p.number_view asc ")
    Page<NewsInfo> findNewByNumberOfView (Pageable pageable);

    Optional<NewsInfo> findById(@Param("id") Long id);



}
