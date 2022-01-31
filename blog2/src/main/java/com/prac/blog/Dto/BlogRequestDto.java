package com.prac.blog.Dto;

import com.prac.blog.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BlogRequestDto {
    private String title;
    private User user;
    private String content;
}
