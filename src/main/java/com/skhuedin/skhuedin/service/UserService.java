package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(Long id, User user) {
        User findUser = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
        findUser.update(user);
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
        User newUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 user 가 존재하지 않습니다."));

        //비밀번호 확인 등의 유효성 검사 진행
        return jwtTokenProvider.createToken(email);
    }

    public String getToken(String Token) {
        return jwtTokenProvider.getSubject(Token);
    }

    public String signUp(User user) { // 회원가입

            save(user);

      return signIn(user);
    }

    public User login(UserMainResponseDto user) { // 회원가입
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        return findUser;
    }

    public String signIn(User user) { // 로그인
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        if (!findUser.getPassword().equals(user.getPassword()))
            throw new IllegalArgumentException("암호 불일치");

        // 로그인 전 변경 사항이 있는지 체크
        if (!checkUpdate(findUser, user)) {
            update(findUser.getId(), user);
            return createToken(findUser.getEmail());
        }
        return createToken(findUser.getEmail());
    }

    public boolean checkUpdate(User findUser, User newUser) {
        return findUser.equals(newUser);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).get();
    }
}