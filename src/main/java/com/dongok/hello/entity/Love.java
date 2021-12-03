package com.dongok.hello.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "love")
@Data
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,name = "love_id")
    private AccountCustomer love;


    @ManyToOne
    @JoinColumn(nullable = false,name = "newsinfolove_id")
    private NewsInfo newsinfolove;

    @Column(name = "date_created",nullable = false)
    private Date dateCreated;

    @Column(name = "status")
    private boolean status;
}
