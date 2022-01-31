package com.prac.blog.models;

import com.prac.blog.Dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Blog extends Timestamped  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private  String content;

    @ManyToOne
    @JoinColumn(nullable=false)
    private User user;

    @OneToMany(mappedBy = "blog",cascade = CascadeType.REMOVE)
    List<Comment> comment = new ArrayList<>();



    public Blog(BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = requestDto.getUser();
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = requestDto.getUser();
    }
}

