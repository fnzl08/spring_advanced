package com.prac.blog.Controller;

import com.prac.blog.Dto.CommentRequestDto;
import com.prac.blog.models.Blog;
import com.prac.blog.models.Comment;
import com.prac.blog.repository.BlogRepository;
import com.prac.blog.repository.CommentRepository;
import com.prac.blog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogRepository blogRepository;

    @PostMapping("/api/hjblog/{id}/comment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Blog blog = blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.setUser(userDetails.getUser());
        comment.setBlog(blog);
        commentRepository.save(comment);
        return "redirect:/api/hjblog/{id}";
    }

    @PutMapping("/apihjblog/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setContent(requestDto.getContent());

        commentRepository.save(comment);
        return "redirect:/api/board/{id}";
    }

    @DeleteMapping("/api/hjblog/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/api/hjblog/{id}";
    }
}
