package com.prac.blog.Dto;

import com.prac.blog.models.User;
import com.prac.blog.models.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
    private User user;
    private Blog blog;
}
