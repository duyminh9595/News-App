package com.dongok.hello.service;

import com.dongok.hello.entity.NewsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    public List<NewsInfo> findNewsByName(String name);
    public List<NewsInfo>getAllNews();
    public List<NewsInfo>getNewsByTheLoaId(Long id);
    public NewsInfo findNewById(Long id);
}
