package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.comment.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"question", "question.targetUser", "question.writerUser", "writerUser"})
    @Query("select c " +
            "from Comment c " +
            "where c.question.id = :questionId")
    List<Comment> findByQuestionId(@Param("questionId") Long questionId);

    @Query("select c " +
            "from Comment c " +
            "where c.writerUser.id = :userId")
    List<Comment> findByWriterUserId(@Param("userId") Long userId);
}