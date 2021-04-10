package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 회원 가입
     */
    @Transactional //변경
    public Long join(User user){
//        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("user 를 찾을 수 없습니다."));
    }

    /**
     * 유저 전체 조회
     */
    public List<User>findAll(){
        return userRepository.findAll();
    }

    /**
     * 유저 중복 확인
     */
//    private void validateDuplicateUser(User user) {
//        User findUser = userRepository.findByEmail(user.getEmail());
//        if(findUser !=null){
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
}
