package com.prac.blog.domain;

import com.prac.blog.Dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor //lombok, 기본 생성자를 자동으로
@Entity //그냥 클래스 아니고 이거 데이터베이스로 테이블 역 할거야
@Getter //get 함수 일괄 생성

//생성, 수정 시간 자동으로 만들어준다. 상속
public class Blog extends Timestamped  {

    // id 값을 primary key로 사용하겠다.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //자동 증가 명령
    private Long id;

    @Column(nullable = false)  //컬럼값이고 반드시 존재해야 함을 나타낸다. not null
    private String title;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    private  String content;

    public Blog(String title, String name, String contents) {
        this.title = title;
        this.name = name;
        this.content = contents;
    }


    public Blog( BlogRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
    }

    //업데이트라는 메소드를 만들고 전달받는, 내가 변경할 정보를 가져오는 blog와 내 정보 바꿔줌,
    // trnasactional 덕으로 자동반영, 반환타입 없고 void 정보 전달받는건 dto
    public void  updaqte(BlogRequestDto requestDto){  //인풋
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
