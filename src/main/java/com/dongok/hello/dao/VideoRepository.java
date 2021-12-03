package com.dongok.hello.dao;

import com.dongok.hello.entity.NewsType;
import com.dongok.hello.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "video",collectionResourceRel = "video")
@CrossOrigin
public interface VideoRepository extends JpaRepository<Video,Long> {
    @Query("select p from Video  p   order by p.Date_Created desc ")
    List<Video> findListVideos();
}
