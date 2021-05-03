package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * org.springframework.data.jpa.repository 를 상속받은 JPARepository를 상속 받음으로써
 *
 * @Repository 를 생략해도 빌드 시점에서 repository 로 인식하기 때
 */

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
