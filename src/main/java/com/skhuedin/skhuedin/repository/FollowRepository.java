package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @EntityGraph(attributePaths = {"toUser", "toUser.blog", "fromUser", "fromUser.blog"})
    @Query("select f " +
            "from Follow f " +
            "where f.toUser.id = :id")
    List<Follow> findByToUserId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"toUser", "toUser.blog", "fromUser", "fromUser.blog"})
    @Query("select f " +
            "from Follow f " +
            "where f.fromUser.id = :id")
    List<Follow> findByFromUserId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"toUser", "toUser.blog", "fromUser", "fromUser.blog"})
    @Query("select f " +
            "from Follow f " +
            "where f.fromUser.id = :fromUserId and f.toUser.id = :toUserId")
    Optional<Follow> findByFromUserIdAndToUserId(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);
}