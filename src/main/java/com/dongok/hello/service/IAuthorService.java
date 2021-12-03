package com.dongok.hello.service;

import com.dongok.hello.dto.AddVideoDTO;
import com.dongok.hello.dto.AuthorLoginDTO;
import com.dongok.hello.dto.AuthorRegisterDTO;
import com.dongok.hello.dto.NewsDTO;
import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsInfo;
import org.springframework.stereotype.Service;


public interface IAuthorService {
    public Author registerAuthor(AuthorRegisterDTO authorRegisterDTO);
    public boolean generateNew(NewsDTO newsDTO);
    public boolean generateVideo(AddVideoDTO addVideoDTO);
}
