package com.prac.blog.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter //private이니까 정보 설정 위해
@NoArgsConstructor //fianl 오류 안나게

public class BlogRequestDto {
    private String title; //private로 선언한 애가 final되면 바로 필요한 생성자 만들어준다.
    private String name;
    private String content;

    public BlogRequestDto(String title, String name, String content){
        this.title = title;
        this.name = name;
        this.content = content;
    }
}
