package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTargetUserIdOrderByLastModifiedDateDesc(Long id);

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    Page<Question> findByTargetUserId(Long id, Pageable pageable);
}