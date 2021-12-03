package com.dongok.hello.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accounts_customer")
@Data
public class AccountCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "enable_status")
    private boolean enable_status;

    @Column(name = "disable_reason")
    private String disable_reason;

    @Column(name = "confirm_email")
    private boolean confirm_email;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "commend")
    private Set<Commend> listCommends ;

    @Override
    public String toString() {
        return "";
    }

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "love")
    private List<Love> loveSet ;

    public void AddLove(Love love)
    {
        if(love!=null)
        {
            if(loveSet==null)
                loveSet=new ArrayList<>();
            loveSet.add(love);
            love.setLove(this);
        }
    }

}
