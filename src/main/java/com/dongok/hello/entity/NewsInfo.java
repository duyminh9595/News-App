package com.dongok.hello.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "news")
@Data
public class NewsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String Title;

    @Column(name = "content",columnDefinition="TEXT")
    private String Content;

    @Column(name = "date_created")
    private Date Date_Created;

    @Column(name = "number_view")
    private Long number_view;

    @Column(name = "img_thumbnail")
    private String ImageThumbnail;


    @ManyToOne
    @JoinColumn(nullable = false,name = "type_id")
    private NewsType type;

    @ManyToOne
    @JoinColumn(nullable = false,name = "author_id")
    private Author author;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "newsinfolove")
    private List<Love> newsinfolove ;

    @Override
    public String toString() {
        return "";
    }

    public void AddLove(Love love)
    {
        if(love!=null)
        {
            if(newsinfolove==null)
                newsinfolove=new ArrayList<>();
            newsinfolove.add(love);
            love.setNewsinfolove(this);
        }
    }

}
