package com.prac.blog.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
//상속했을 때, 멤버 변수가 있을텐데 그걸 컬럼으로 인식하게
@MappedSuperclass
//생성 수정 시간을 자동으로 반영하도록 설정. Entity는 blog같은 테이블을 주시하겠다. auditing 즉 수정해주고 자동으로 반영해주겠다.
@EntityListeners(AuditingEntityListener.class)

//abstract는 상속으로만 쓸 수 있다는 것. 직접 구현 안됨
public abstract class Timestamped{

    //이렇게 멤버 두개 가지고, 자료형은 localdatetime 자바의 시간형태
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}