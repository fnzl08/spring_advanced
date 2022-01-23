package com.prac.blog;

import com.prac.blog.domain.Blog;
import com.prac.blog.domain.BlogRepository;
import jdk.javadoc.internal.doclets.formats.html.Contents;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//생성 수정일 관련
@EnableJpaAuditing

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

    }

    @Bean
    public CommandLineRunner demo(BlogRepository repository) {
        return (args) -> {
            repository.save(new Blog("사실은", "ㅎㅈ", "아직은 항해보단 조난에 가깝.."));
        };
    }
}
