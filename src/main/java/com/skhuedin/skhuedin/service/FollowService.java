package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
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

        if (followRepository.existsByToUserIdAndFromUserId(requestDto.getToUserId(), requestDto.getFromUserId())) {
            return followRepository.findByToUserIdAndFromUserId(
                    requestDto.getToUserId(), requestDto.getFromUserId())
                    .getId();
        }

        User toUser = getUser(requestDto.getToUserId());
        User fromUser = getUser(requestDto.getFromUserId());

        return followRepository.save(requestDto.toEntity(toUser, fromUser)).getId();
    }

    @Transactional
    public Long update(Long id, FollowSaveRequestDto requestDto) {
        Follow follow = getFollow(id);
        User toUser = getUser(requestDto.getToUserId());
        User fromUser = getUser(requestDto.getFromUserId());

        follow.UpdateFollow(requestDto.toEntity(toUser, fromUser));

        return follow.getId();
    }

    @Transactional
    public void delete(Long id) {
        Follow follow = getFollow(id);
        followRepository.delete(follow);
    }

    public FollowMainResponseDto findById(Long id) {
        Follow follow = getFollow(id);

        return new FollowMainResponseDto(follow);
    }

    public List<FollowMainResponseDto> findAll() {
        return followRepository.findAll().stream()
                .map(follow -> new FollowMainResponseDto(follow))
                .collect(Collectors.toList());
    }

    public List<FollowMainResponseDto> findByToUserId(Long toUserId) {
        return followRepository.findByToUserId(toUserId)
                .stream()
                .map(follow -> new FollowMainResponseDto(follow))
                .collect(Collectors.toList());
    }

    public List<FollowMainResponseDto> findByFromUserId(Long fromUserId) {
        return followRepository.findByFromUserId(fromUserId)
                .stream()
                .map(follow -> new FollowMainResponseDto(follow))
                .collect(Collectors.toList());
    }

    private Follow getFollow(Long id) {
        return followRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 follow 가 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }
}
