package com.dongok.hello.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

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

    @Column(name = "admin_confirm")
    private boolean adminConfirm;


    @Column(name = "date_admin_confirm")
    private Date date_admin_confirm;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "author")
    private Set<NewsInfo> newsInfoSet ;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "authorvideo")
    private Set<Video> videoSet ;


    public void AddNews(NewsInfo newsInfo)
    {
        if(newsInfo!=null)
        {
            if(newsInfoSet==null)
            {
                newsInfoSet=new HashSet<NewsInfo>();
            }
            newsInfoSet.add(newsInfo);
            newsInfo.setAuthor(this);
        }
    }
    public void AddVideos(Video video)
    {
        if(video!=null)
        {
            if(videoSet==null)
            {
                videoSet=new HashSet<Video>();
            }
            videoSet.add(video);
            video.setAuthorvideo(this);
        }
    }

}
