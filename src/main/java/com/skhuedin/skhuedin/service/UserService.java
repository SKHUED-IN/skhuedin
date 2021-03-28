package com.skhuedin.skhuedin.service;



import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        return userRepository.save(user).getId();
    }

}
