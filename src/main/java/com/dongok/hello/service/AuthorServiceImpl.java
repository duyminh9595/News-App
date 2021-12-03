package com.dongok.hello.service;

import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.dao.NewsInfoRepository;
import com.dongok.hello.dao.NewsTypeRepository;
import com.dongok.hello.dao.VideoRepository;
import com.dongok.hello.dto.AddVideoDTO;
import com.dongok.hello.dto.AuthorLoginDTO;
import com.dongok.hello.dto.AuthorRegisterDTO;
import com.dongok.hello.dto.NewsDTO;
import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.entity.NewsType;
import com.dongok.hello.entity.Video;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@AllArgsConstructor
@Service
public class AuthorServiceImpl implements IAuthorService {

    private NewsInfoRepository newsInfoRepository;
    private AuthorRepository authorRepository;
    private NewsTypeRepository newsTypeRepository;
    private ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private VideoRepository videoRepository;
    @Override
    public Author registerAuthor(AuthorRegisterDTO authorRegisterDTO) {
        Author author=objectMapper.convertValue(authorRegisterDTO,Author.class);
        Author authorInDb= authorRepository.findData(author.getEmail());
        if(authorInDb==null)
        {
            author.setPassword(passwordEncoder.encode(author.getPassword()));
            authorRepository.save(author);
            return author;
        }
        else
            return null;
    }

    @Override
    public boolean generateNew(NewsDTO newsDTO) {
        NewsType newsType=newsTypeRepository.findData(newsDTO.getTypeId());
        Author author=authorRepository.findData(SecurityContextHolder.getContext().getAuthentication().getName());
        if(newsType!=null && author!=null)
        {
            NewsInfo newsInfo=objectMapper.convertValue(newsDTO,NewsInfo.class);
            newsInfo.setNumber_view(0l);
            newsInfo.setId(0l);
            newsInfo.setDate_Created(new Date());
            author.AddNews(newsInfo);
            newsType.AddNews(newsInfo);

            newsInfoRepository.save(newsInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean generateVideo(AddVideoDTO addVideoDTO) {
        Author author=authorRepository.findData(SecurityContextHolder.getContext().getAuthentication().getName());
        if(author!=null)
        {
            Video video=new Video();
            video.setLink(addVideoDTO.getUrl());
            video.setDate_Created(new Date());
            author.AddVideos(video);
            videoRepository.save(video);
            return true;
        }
        return false;
    }


}
