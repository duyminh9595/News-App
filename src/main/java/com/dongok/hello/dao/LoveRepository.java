package com.dongok.hello.dao;

import com.dongok.hello.entity.Love;
import com.dongok.hello.entity.NewsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "love",collectionResourceRel = "love")
@CrossOrigin
public interface LoveRepository extends JpaRepository<Love,Long> {
    @Query("select p from Love  p  where p.newsinfolove.id=?1 and p.love.id=?2")
    Love findByUserIdAndNewId(Long neweid,Long userid);

    @Query("select p.newsinfolove from Love  p where p.love.id=?1 and p.status=?2 order by p.dateCreated desc ")
    List<NewsInfo> findByUserIdLolve(Long id,boolean status);

    @Query("select p from Love  p where p.love.id=?1 and p.status=?2 and p.newsinfolove.id=?3 order by p.dateCreated desc ")
    Love findByUserIdLolveAndNewId(Long useridid,boolean status,Long newid);
}
