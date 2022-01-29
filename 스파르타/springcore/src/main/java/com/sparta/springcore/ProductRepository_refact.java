package com.sparta.springcore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }

//이거 사용하려면 스프링데이터jpa 문법을 익혀야한다. 서비스의 createProduct로 가셍