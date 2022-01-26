package com.prac.blog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//jpa가져다가 쓸거임. blog라는 애고 id의 자료형이 long.
//jpa에서 미리 작성된 메소드 모음집을 내가 가져다 쓸거다. (인터페이스)
public interface BlogRepository extends JpaRepository<Blog, Long>{
    //오타와 modified 섞임의 콜라보
    List<Blog> findAllByOrderByCreatedAtDesc();
}
//controller와 연계