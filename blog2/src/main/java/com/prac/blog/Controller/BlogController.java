package com.prac.blog.Controller;


import com.prac.blog.Dto.BlogRequestDto;
import com.prac.blog.Service.BlogService;
import com.prac.blog.models.User;
import com.prac.blog.models.Blog;
import com.prac.blog.models.Comment;
import com.prac.blog.repository.BlogRepository;
import com.prac.blog.repository.CommentRepository;
import com.prac.blog.repository.UserRepository;
import com.prac.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller

public class BlogController{

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/api/hjblog")
    public String getIndex(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(size=5) Pageable pageable){
        Page<Blog> blog = blogRepository.findAllByOrderByModifiedAtDesc(pageable);

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        // 현재 페이지 넘버 - 4 (4는 임의로 정한값)을 뺀값을 보여줄 페이지 값에 첫번째 값으로 지정
        // 현재 페이지 넘버 + 4 을 더한값을 보여줄 페이지에서 끝값으로 표시
        int startPage = Math.max(1, blog.getPageable().getPageNumber() - 4); //그런데 음수가 나올수 있으니 max() 함수를 이용해 0보다 작은값은 나오지 않도록 만들어줌
        int endPage = Math.min(blog.getTotalPages(),blog.getPageable().getPageNumber() + 4); // 마찬가지로 최대페이지수를 초과하지않게 min()함수 걸어줌

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("board",blog);
        return "index";
    }



    //게시글 작성 페이지
    @GetMapping("/api/hjblog/write")
    public String getNotice(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Blog blog = new Blog();
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("blog", blog);
        return "write";
    }

    // 게시글 작성
    @PostMapping("/api/hjblog/write")
    public String createBlog(@AuthenticationPrincipal UserDetailsImpl userDetails,@ModelAttribute BlogRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return "redirect:/api/hjblog";
    }


    // 게시글 특정 조회
    @GetMapping("/api/hjblog/{id}")
    public String getOneBlog(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Blog blog =  blogRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시글 존재하지 않습니다.")
        );

        List <Comment> comment = commentRepository.findByBlogIdOrderByModifiedAtDesc(id);
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user", userDetails.getUser().getUsername()) ;
        }
        model.addAttribute("editcomment", new Comment());
        model.addAttribute( "postcomment", new Comment());
        model.addAttribute("comment",comment);
        model.addAttribute("blog", blog);

        return "deatil";
    }


    //게시글 수정
    @GetMapping("/api/hjblog/{id}/edit")
    public String getEditBlog(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(userDetails == null){
            model.addAttribute("user", "null");
        }else{
            model.addAttribute("user", userDetails.getUser().getUsername());
        }
        model.addAttribute("blog", blog);
        return "edit";
    }




    //Delete
    @DeleteMapping("/api/hjblog/{id}")
    public String deleteBlog(@PathVariable Long id){
        blogRepository.deleteById(id);
        return "redirect:/";
    }


    //Update
    @PutMapping("/api/hjblog/{id}/edit")
    public String updateBlog(@PathVariable Long id, @ModelAttribute BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        requestDto.setUser(userDetails.getUser());
        blogService.update(id, requestDto);
        return "redirect:/";
    }

}
