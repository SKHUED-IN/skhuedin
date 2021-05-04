package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Follow;

import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.dto.follow.FollowSaveRequestDto;

import com.skhuedin.skhuedin.repository.FollowRepository;
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

    @Transactional
    public Long save(FollowSaveRequestDto requestDto) {
        Follow follow = getFollow(requestDto.getId());
        return followRepository.save(requestDto.toEntity(follow)).getId();
    }

    @Transactional
    public Long update(Long id, FollowSaveRequestDto requestDto) {
        Follow follow = getFollow(id);
        follow.Update(requestDto.toEntity(follow));

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

    private Follow getFollow(Long id) {
        return followRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 follow 가 존재하지 않습니다. id=" + id));
    }
}