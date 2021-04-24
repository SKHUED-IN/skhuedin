package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        User findUser = userRepository.findById(id).get();
        findUser.update(user);
    }

    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findById(id).get(); // 영속성 컨텍스트에 등록
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


    public void signUp(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail()).get();
        if (checkUser != null) {
            signIn(user);
        } else {
            save(user);
            signIn(user);
        }
    }

    public String signIn(User user) {
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        if (!findUser.getPassword().equals(user.getPassword()))
            throw new IllegalArgumentException("암호 불일치");

        return createToken(findUser.getEmail());
    }

    public User findByName(String name) {
        return userRepository.findByName(name).get();
    }
}