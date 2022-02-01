package com.sparta.jpa.service;

import com.sparta.jpa.model.User;
import com.sparta.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser() {
// 테스트 회원 "user1" 객체 추가 , 빨간건 브레이크포인트
        //createuser 찾아가면 controller에서 get user/create호출 , user로 들어와서
        //userservice에 createuser로 들어 오는 순간 스탑
        User beforeSavedUser = new User("user1", "정국", "불족발");
// 회원 "user1" 객체를 영속화. 객체 만든걸 디비에 적용해줘라. 결과를 saveduser로 받는
        User savedUser = userRepository.save(beforeSavedUser);

// beforeSavedUser: 영속화되기 전 상태의 자바 일반객체
// savedUser:영속성 컨텍스트 1차 캐시에 저장된 객체
        //assert는 이게 맞다고 강하게 주장하는것. beforesaveduser와 saveduser 들여다보면 내용은 같은데 디버깅 해서 한단계식 넘겨보면 주소가 다르다.
        //save한 saveduser가 좀 더 정확
        assert(beforeSavedUser != savedUser);

// 회원 "user1" 을 조회. 1차 캐시에 있는 user1이 온다. 디버킹해서 확인해 보면 founduser와 user는 같은 값
        User foundUser1 = userRepository.findById("user1").orElse(null);
        assert(foundUser1 == savedUser);

// 회원 "user1" 을 또 조회
        User foundUser2 = userRepository.findById("user1").orElse(null);
        assert(foundUser2 == savedUser);

// 회원 "user1" 을 또또 조회
        User foundUser3 = userRepository.findById("user1").orElse(null);
        assert(foundUser3 == savedUser);

        return foundUser3; //1차 캐시에서 가져오기 때문에 다 같은거 -> controller로 가보자
    }

    public User deleteUser() { //controller 가보면 userdelete와 getuserdelete
// 테스트 회원 "user1" 객체만 추가
        User firstUser = new User("user1", "지민", "엄마는 외계인");
// 회원 "user1" 객체를 영속화, 디비 추가
        User savedFirstUser = userRepository.save(firstUser);

// 회원 "user1" 삭제, 쿼리 날라가고 디비에서 사라짐
        userRepository.delete(savedFirstUser);

// 회원 "user1" 조회, 없으니까 null이 나옴
        User deletedUser1 = userRepository.findById("user1").orElse(null);
        assert(deletedUser1 == null);

// -------------------
// 테스트 회원 "user1" 객체를 다시 추가
// 회원 "user1" 객체 추가. 위랑 내용은 동일한데 객체가 달라짐
        User secondUser = new User("user1", "지민", "엄마는 외계인");

// 회원 "user1" 객체를 영속화. 내용은 같지만 save해보면 다르다
        User savedSecondUser = userRepository.save(secondUser);
        assert(savedFirstUser != savedSecondUser);
        assert(savedFirstUser.getUsername().equals(savedSecondUser.getUsername()));
        assert(savedFirstUser.getNickname().equals(savedSecondUser.getNickname()));
        assert(savedFirstUser.getFavoriteFood().equals(savedSecondUser.getFavoriteFood()));

// 회원 "user1" 조회
        User foundUser = userRepository.findById("user1").orElse(null);
        assert(foundUser == savedSecondUser);

        return foundUser;
    }

    public User updateUserFail() {
// 회원 "user1" 객체 추가
        User user = new User("user1", "뷔", "콜라");
// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("얼굴천재");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("버거킹");

// 회원 "user1" 을 조회
        User foundUser = userRepository.findById("user1").orElse(null);
// 중요!) foundUser 는 DB 값이 아닌 1차 캐시에서 가져오는 값
        assert(foundUser == savedUser);
        assert(foundUser.getUsername().equals(savedUser.getUsername()));
        assert(foundUser.getNickname().equals(savedUser.getNickname()));
        assert(foundUser.getFavoriteFood().equals(savedUser.getFavoriteFood()));

        return foundUser;
    }

    public User updateUser1() {
// 테스트 회원 "user1" 생성
        User user = new User("user1", "RM", "고기");
// 회원 "user1" 객체를 영속화
        User savedUser1 = userRepository.save(user);

// 회원의 nickname 변경
        savedUser1.setNickname("남준이");
// 회원의 favoriteFood 변경
        savedUser1.setFavoriteFood("육회");

// user1 을 저장
        User savedUser2 = userRepository.save(savedUser1);
        assert(savedUser1 == savedUser2);

        return savedUser2;
    }

    @Transactional
    public User updateUser2() {
// 테스트 회원 "user1" 생성
// 회원 "user1" 객체 추가
        User user = new User("user1", "진", "꽃등심");
// 회원 "user1" 객체를 영속화
        User savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("월드와이드핸섬 진");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("까르보나라");

        return savedUser;
    }
}