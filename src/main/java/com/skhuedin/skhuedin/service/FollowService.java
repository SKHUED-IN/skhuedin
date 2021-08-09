package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.follow.Follow;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.follow.FollowDeleteRequestDto;
import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.dto.follow.FollowSaveRequestDto;
import com.skhuedin.skhuedin.repository.FollowRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(FollowSaveRequestDto requestDto) {

        Follow follow = followRepository.findByFromUserIdAndToUserId(requestDto.getFromUserId(), requestDto.getToUserId())
                .orElse(null);

        if (follow != null) {
            return follow.getId();
        }

        User toUser = getUser(requestDto.getToUserId());
        User fromUser = getUser(requestDto.getFromUserId());

        return followRepository.save(requestDto.toEntity(fromUser, toUser)).getId();
    }

    public FollowMainResponseDto findById(Long id) {
        Follow follow = getFollow(id);

        return new FollowMainResponseDto(follow);
    }

    public List<FollowMainResponseDto> findByFromUserId(Long fromUserId) {
        return followRepository.findByFromUserId(fromUserId)
                .stream()
                .map(follow -> new FollowMainResponseDto(follow))
                .collect(Collectors.toList());
    }

    public List<FollowMainResponseDto> findByToUserId(Long toUserId) {
        return followRepository.findByToUserId(toUserId)
                .stream()
                .map(follow -> new FollowMainResponseDto(follow))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(FollowDeleteRequestDto requestDto) {
        Follow follow = followRepository.findByFromUserIdAndToUserId(requestDto.getFromUserId(), requestDto.getToUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 follow 입니다."));

        followRepository.delete(follow);
    }

    /* private 메소드 */
    private Follow getFollow(Long id) {
        return followRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 follow 가 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }
}
