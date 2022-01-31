package com.prac.blog.repository;

import com.prac.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdOrderByModifiedAtDesc(Long id);
}
