package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @EntityGraph(attributePaths = {"writerUser"})
    Page<Question> findByTargetUserId(Long id, Pageable pageable);

    @Query("select q from Question q where q.writerUser.id = :userId")
    List<Question> findQuestionByUserId(@Param("userId") Long userId);

    @Query("select q from Question q where q.targetUser.id = :userId")
    List<Question> findQuestionByTargetUserId(@Param("userId") Long userId);

}