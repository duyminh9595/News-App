package com.dongok.hello.controller;


import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.dao.NewsTypeRepository;
import com.dongok.hello.dao.VideoRepository;
import com.dongok.hello.dto.GetVideoToAndroidDTO;
import com.dongok.hello.dto.ListTypesDTO;
import com.dongok.hello.dto.ReadNewDTO;
import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.entity.NewsType;
import com.dongok.hello.entity.Video;
import com.dongok.hello.service.INewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/news/app")
public class NewsController {
    private INewService iNewService;
    private AuthorRepository authorRepository;
    private NewsTypeRepository newsTypeRepository;
    private VideoRepository videoRepository;
    @GetMapping("/home")
    public ResponseEntity GetNewHomePage()
    {
        List<NewsInfo>newsInfoList=iNewService.getAllNews();
        List<ReadNewDTO>readNewDTOList=new ArrayList<>();
        for(NewsInfo newsInfo:newsInfoList )
        {
            ReadNewDTO readNewDTO=new ReadNewDTO();
            readNewDTO.setId(newsInfo.getId());
            readNewDTO.setIdtype(newsInfo.getType().getId());
            readNewDTO.setDate_Created(newsInfo.getDate_Created().toString());
            readNewDTO.setAuthor(newsInfo.getAuthor().getEmail());
            readNewDTO.setTitle(newsInfo.getTitle());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTO.setImageThumbnail(newsInfo.getImageThumbnail());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTOList.add(readNewDTO);
        }
        return ResponseEntity.ok(readNewDTOList);
    }
    @GetMapping("/findbycontent")
    public ResponseEntity GetNewByName(@RequestParam String name)
    {
        ArrayList<ReadNewDTO>readNewDTOS=new ArrayList<>();
        List<NewsInfo> newsInfos=iNewService.findNewsByName(name);
        for(NewsInfo data:newsInfos)
        {
            ReadNewDTO readNewDTO=new ReadNewDTO();
            readNewDTO.setId(data.getId());
            readNewDTO.setContent(data.getContent());
            readNewDTO.setDate_Created(data.getDate_Created().toString());
            Author author=authorRepository.findDataById(data.getAuthor().getId());
            readNewDTO.setAuthor(author.getEmail());
            readNewDTO.setTitle(data.getTitle());
            readNewDTOS.add(readNewDTO);
        }
        return ResponseEntity.ok(readNewDTOS);
    }
    @GetMapping("/findbyid")
    public ResponseEntity GetNewById(@RequestParam(name = "id") Long id)
    {
        NewsInfo newsInfo=iNewService.findNewById(id);
        if(newsInfo!=null) {
            ReadNewDTO readNewDTO=new ReadNewDTO();
            readNewDTO.setId(newsInfo.getId());
            readNewDTO.setIdtype(newsInfo.getType().getId());
            readNewDTO.setContent(newsInfo.getContent());
            readNewDTO.setDate_Created(newsInfo.getDate_Created().toString());
            Author author=authorRepository.findDataById(newsInfo.getAuthor().getId());
            readNewDTO.setAuthor(author.getEmail());
            readNewDTO.setTitle(newsInfo.getTitle());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTO.setImageThumbnail(newsInfo.getImageThumbnail());
            return ResponseEntity.ok(readNewDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/theotheloaiid")
    public ResponseEntity GetNewByTypeId(@RequestParam(name = "id")Long id)
    {
        List<NewsInfo>newsInfos=iNewService.getNewsByTheLoaId(id);
        List<ReadNewDTO>readNewDTOList=new ArrayList<>();
        for(NewsInfo newsInfo:newsInfos )
        {
            ReadNewDTO readNewDTO=new ReadNewDTO();
            readNewDTO.setId(newsInfo.getId());
            readNewDTO.setIdtype(newsInfo.getType().getId());
            readNewDTO.setDate_Created(newsInfo.getDate_Created().toString());
            readNewDTO.setAuthor(newsInfo.getAuthor().getEmail());
            readNewDTO.setTitle(newsInfo.getTitle());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTO.setImageThumbnail(newsInfo.getImageThumbnail());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTOList.add(readNewDTO);
        }
        return ResponseEntity.ok(readNewDTOList);
    }

    @GetMapping("/danhsachtheloai")
    public ResponseEntity GetDanhSachTheLoai()
    {
        List<NewsType>newsTypeList= newsTypeRepository.findListTypes();
        List<ListTypesDTO>listTypesDTOS=new ArrayList<>();
        for(NewsType data:newsTypeList)
        {
            ListTypesDTO listTypesDTO=new ListTypesDTO();
            listTypesDTO.setId(data.getId());
            listTypesDTO.setName(data.getName());
            listTypesDTO.setImageurl(data.getImage_url());
            listTypesDTOS.add(listTypesDTO);
        }
        return ResponseEntity.ok(listTypesDTOS);
    }
    @GetMapping("/danhsachvideo")
    public ResponseEntity GetDanhSachVideo()
    {
        List<Video>videoList=videoRepository.findListVideos();
        List<GetVideoToAndroidDTO>getVideoToAndroidDTOList=new ArrayList<>();
        for(Video data:videoList)
        {
            GetVideoToAndroidDTO getVideoToAndroidDTO=new GetVideoToAndroidDTO();
            getVideoToAndroidDTO.setDate(data.getDate_Created().toString());
            getVideoToAndroidDTO.setAuthorname(data.getAuthorvideo().getFullName());
            getVideoToAndroidDTO.setId(data.getId());
            getVideoToAndroidDTO.setUrl(data.getLink());
            getVideoToAndroidDTOList.add(getVideoToAndroidDTO);
        }
        return ResponseEntity.ok(getVideoToAndroidDTOList);
    }


}
