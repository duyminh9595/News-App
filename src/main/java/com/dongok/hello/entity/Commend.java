package com.dongok.hello.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commend")
@Data
public class Commend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,name = "commend_id")
    private AccountCustomer commend;

    @Column(name = "content",nullable = false)
    private String content;

}
