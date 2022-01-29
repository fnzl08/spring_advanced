package com.sparta.springcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//밑에 보니 repo관련인 것 같은데 configu말고 component쓰면 밑에 에러. alxdp

@Configuration //스프링 기동시 이거부터 읽는다
public class BeanConfiguration {

    @Bean //리턴되는 값을 빈에 등록하겠다. 이거로 repo생성해 객체 리. 리턴하면 ioc 컨테이너 안으로 들어가는 아이콘 나온다.
    public ProductRepository productRepository() {
        String dbUrl = "jdbc:h2:mem:springcoredb";
        String dbId = "sa";
        String dbPassword = "";

        return new ProductRepository(dbUrl, dbId, dbPassword);
    }
}



//사실 이거 지워도 됨. 스프링데이터 jpa가 알아서 해주는 거임
