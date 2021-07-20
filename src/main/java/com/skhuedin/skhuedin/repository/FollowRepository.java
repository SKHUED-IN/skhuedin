package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @EntityGraph(attributePaths = {"toUser", "fromUser"})
    @Query("select f " +
            "from Follow f " +
            "where f.toUser.id = :id")
    List<Follow> findByToUserId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"toUser", "fromUser"})
    @Query("select f " +
            "from Follow f " +
            "where f.fromUser.id = :id")
    List<Follow> findByFromUserId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"toUser", "fromUser"})
    @Query("select f " +
            "from Follow f " +
            "where f.toUser.id = :toUserId and f.fromUser.id = :fromUserId")
    Follow findByToUserIdAndFromUserId(@Param("toUserId") Long toUserId, @Param("fromUserId") Long fromUserId);

    Boolean existsByToUserIdAndFromUserId(Long toUserId, Long fromUserId);
}