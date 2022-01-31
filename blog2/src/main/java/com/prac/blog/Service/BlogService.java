package com.prac.blog.Service;

import com.prac.blog.Dto.BlogRequestDto;
import com.prac.blog.models.Blog;
import com.prac.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor

public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public Long update(Long id, BlogRequestDto requestDto){
        Blog blog = blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("일지가 없어..")
        );
        blog.update(requestDto);
        return blog.getId();
    }
}
