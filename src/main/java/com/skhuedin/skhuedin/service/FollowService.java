package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.repository.CommentRepository;
import com.skhuedin.skhuedin.repository.FollowRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
//
//    private final UserRepository userRepository;
//    private final FollowRepository followRepository;
//
//    @Transactional
//    public Long save(FollowMainResponseDto requestDto) {
//        Follow follow = followRepository.getOne(requestDto.getId());
//
//
//    }
//
//    @Transactional
//    public Long save(BlogSaveRequestDto requestDto) {
//        User user = getUser(requestDto.getUserId());
//
//        return blogRepository.save(requestDto.toEntity(user)).getId();
//    }
}
