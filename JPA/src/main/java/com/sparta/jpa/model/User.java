package com.sparta.jpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User {
    // nullable: null 허용 여부
// unique: 중복 허용 여부 (false 일때 중복 허용)
    @Id //이 부분이 이제 id로 사용되고 있다, pk다.
    //이 name에 맞춰서 필드 네임이 정해진다. 지금은 username이지만 테이블에서는 Id로 쓰겠다.
    @Column(name = "id", nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)  //unique false로 중복 가능
    private String nickname;

    @Column(nullable = false, unique = false) //name = ""로 이름 정해줄 수도
    private String favoriteFood;


    //생성자로 받아온 걸 멤버 변수로 2개 필드 가진 유저테이블 선언
    public User(String username, String nickname, String favoriteFood) {
        this.username = username;
        this.nickname = nickname;
        this.favoriteFood = favoriteFood;
    }
}