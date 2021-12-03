package com.dongok.hello.service;

import com.dongok.hello.dto.NewsDTO;
import com.dongok.hello.dto.TypeDTO;
import com.dongok.hello.entity.NewsType;

public interface IAdminService {
    public NewsType AddNewsType(TypeDTO typeDTO);
    public boolean ConfirmAuthor(String email);
}
