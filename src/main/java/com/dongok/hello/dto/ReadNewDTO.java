package com.dongok.hello.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ReadNewDTO {
    private Long id;

    private String Title;

    private Long idtype;

    private String Content;

    private String Date_Created;

    private Long number_view;

    private String ImageThumbnail;

    private String author;
}
