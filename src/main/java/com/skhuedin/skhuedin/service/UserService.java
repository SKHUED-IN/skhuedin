package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long save(User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    public void update(Long id, UserSaveRequestDto requestDto) {
        User user = getUser(id);
        user.update(requestDto.toEntity());
    }

    @Transactional
    public void updateInfo(Long id, UserSaveRequestDto requestDto) {
        User user = getUser(id);
        user.update(requestDto.toEntity(user));
    }

    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
        ; // 영속성 컨텍스트에 등록
        userRepository.delete(findUser);
    }

    public UserMainResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
        return new UserMainResponseDto(user);
    }

    /**
     * token 으로 회원 가입
     */
    public String createToken(String email) {
        //비밀번호 확인 등의 유효성 검사 진행
        return jwtTokenProvider.createToken(email);
    }

    public String signUp(UserSaveRequestDto requestDto) { // 회원가입
        User user = requestDto.toEntity();
        save(user);
        return signIn(requestDto);
    }

    @Transactional
    public String signIn(UserSaveRequestDto requestDto) { // 로그인
        User findUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        // 로그인 전 변경 사항이 있는지 체크 findUser
        findUser.update(requestDto.toEntity());

        return createToken(findUser.getEmail());
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        // Bearer 검증에 null 값을 넘기기 위해 일부러 이렇게 작성함
        return user.orElse(null);
    }

    public List<UserMainResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserMainResponseDto(user))
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}