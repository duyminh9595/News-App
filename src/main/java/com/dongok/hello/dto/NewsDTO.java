package com.dongok.hello.dto;

import lombok.Data;

@Data
public class NewsDTO {
    private String Title;
    private String Content;
    private String ImageThumbnail;
    private Long typeId;
}
