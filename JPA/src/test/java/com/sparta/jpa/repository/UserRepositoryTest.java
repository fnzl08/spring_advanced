package com.sparta.jpa.repository;

import com.sparta.jpa.model.User;  //user가 많기 때문에 이거 설정 잘 해주기
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    public void create() { //id 다 다른걸로 생성
// 회원 "user1" 객체 생성
        User instance1 = new User("user1", "정국", "불족발");
// 회원 "user1" 객체 또 생성
        User instance2 = new User("user1", "정국", "불족발");
        assert(instance2 != instance1);
// 회원 "user1" 객체 또또 생성
        User instance3 = new User("user1", "정국", "불족발");
        assert(instance3 != instance2);

// 회원 "user1" 객체 추가
        User user1 = new User("user1", "정국", "불족발");

// 회원 "user1" 객체의 ID 값이 없다가..
        userRepository.save(user1);

// 테스트 회원 데이터 삭제
        userRepository.delete(user1);
    }

    @Order(2)
    @Test
    public void findUser() {
// ------------------------------------
// 회원 "user1" 객체 추가
        User beforeSavedUser = new User("user1", "정국", "불족발");

// 회원 "user1" 객체를 영속화.
        User savedUser = userRepository.save(beforeSavedUser);

// 회원 "user1" 을 조회. 엥 주소 바뀜
        User foundUser1 = userRepository.findById("user1").orElse(null);
        assert(foundUser1 != savedUser);
//      foundUser1.setNickname("") 이거 쓰면 닉네임 바꿀 수 있다. 지금은 변수도 객체도 다르니 2,3의 닉네임은 변하지 않는다. 그럼 어떤게 맞는걸까
        //동일성 보장이 안된다.

// 회원 "user1" 을 또 조회. 엥 또 다름
        User foundUser2 = userRepository.findById("user1").orElse(null);
        assert(foundUser2 != savedUser);

// 회원 "user1" 을 또또 조회. 디버깅해보면 select가 뜬다. 그니까 계속 디비에서 가져오고 있는 것
        User foundUser3 = userRepository.findById("user1").orElse(null);
        assert(foundUser3 != savedUser);
        //1차 캐시가 항상 되는건 아니구나. 스프링 내부에서만 작동

// ------------------------------------
// 테스트 회원 데이터 삭제
        userRepository.delete(beforeSavedUser);
    }
}
