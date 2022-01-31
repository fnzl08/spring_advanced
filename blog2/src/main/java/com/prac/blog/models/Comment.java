package com.prac.blog.models;

import com.prac.blog.Dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content  ;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Blog blog;

    public Comment(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
        this.user = requestDto.getUser();
        this.blog = requestDto.getBlog();
    }
}
