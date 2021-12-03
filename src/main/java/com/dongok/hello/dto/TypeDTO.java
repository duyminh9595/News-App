package com.dongok.hello.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TypeDTO {
    private String name;

    private String description;
}
