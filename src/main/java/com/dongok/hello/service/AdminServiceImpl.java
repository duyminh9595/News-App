package com.dongok.hello.service;

import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.dao.NewsTypeRepository;
import com.dongok.hello.dto.NewsDTO;
import com.dongok.hello.dto.TypeDTO;
import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements IAdminService{
    private ObjectMapper objectMapper;
    private NewsTypeRepository newsTypeRepository;
    private AuthorRepository authorRepository;
    @Override
    public NewsType AddNewsType(TypeDTO typeDTO) {
        NewsType newsType=objectMapper.convertValue(typeDTO,NewsType.class);
        newsTypeRepository.save(newsType);
        if(newsType!=null)
            return newsType;
        else
            return null;
    }

    @Override
    public boolean ConfirmAuthor(String email) {
        Author authorInDb= authorRepository.findData(email);
        if(authorInDb!=null)
        {
            authorInDb.setAdminConfirm(true);
            authorInDb.setDate_admin_confirm(new Date(System.currentTimeMillis()));
            authorRepository.save(authorInDb);
            return true;
        }
        return false;
    }
}
