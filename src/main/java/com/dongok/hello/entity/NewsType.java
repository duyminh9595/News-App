package com.dongok.hello.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="image_url")
    private String image_url;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "type")
    private Set<NewsInfo> news ;

    public void AddNews(NewsInfo newsInfo)
    {
        if(newsInfo!=null)
        {
            if(news==null)
            {
                news=new HashSet<NewsInfo>();
            }
            news.add(newsInfo);
            newsInfo.setType(this);
        }
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", news=" + news +
                '}';
    }
}
