package com.dongok.hello.dto;

import lombok.Data;

@Data
public class GetVideoToAndroidDTO {
    private Long id;
    private String url;
    private String date;
    private String authorname;
}
