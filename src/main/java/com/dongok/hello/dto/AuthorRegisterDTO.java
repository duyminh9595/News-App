package com.dongok.hello.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AuthorRegisterDTO {
    private String fullName;


    private String email;

    private String password;
}
