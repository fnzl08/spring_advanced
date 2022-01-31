package com.prac.blog.repository;

import com.prac.blog.models.Blog;
import com.prac.blog.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>{

    Page<Blog> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
