package com.dongok.hello.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private String image_url;
}
