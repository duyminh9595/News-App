package com.dongok.hello.service;

import com.dongok.hello.dao.NewsInfoRepository;
import com.dongok.hello.entity.NewsInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NewServiceImpl implements INewService{
    private NewsInfoRepository newsInfoRepository;
    @Override
    public List<NewsInfo> findNewsByName(String name) {
        List<NewsInfo>newsInfos=new ArrayList<NewsInfo>();
        newsInfos=  newsInfoRepository.findByContent(name);
        return newsInfos;
    }

    @Override
    public List<NewsInfo> getAllNews() {
        List<NewsInfo>newsInfos=newsInfoRepository.findNewsAll();
        return newsInfos;
    }

    @Override
    public List<NewsInfo> getNewsByTheLoaId(Long id) {
        List<NewsInfo>newsInfos=newsInfoRepository.findByTheLoaiId(id);
        return newsInfos;
    }

    @Override
    public NewsInfo findNewById(Long id) {
        NewsInfo newsInfo=newsInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if(newsInfo!=null)
        {
            newsInfo.setNumber_view(newsInfo.getNumber_view()+1);
            newsInfoRepository.save(newsInfo);
        }
        return newsInfo;
    }
}
