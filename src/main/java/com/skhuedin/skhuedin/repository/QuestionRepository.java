package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "where q.targetUser.id = :id " +
            "order by q.createdDate desc ") // 생성날짜 내림차순 => 최신순
    Page<Question> findByTargetUserId(@Param("id") Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "where q.writerUser.id = :userId")
    List<Question> findByWriterUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "where q.targetUser.id = :userId")
    List<Question> findByTargetUserId(@Param("userId") Long userId);

    /* admin 전용 */
    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "order by q.lastModifiedDate desc ")
    Page<Question> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "where q.writerUser.name like %:writerUserName% " +
            "order by q.lastModifiedDate desc ")
    Page<Question> findByWriterUserName(Pageable pageable, @Param("writerUserName") String writerUserName);

    @EntityGraph(attributePaths = {"targetUser", "writerUser"})
    @Query("select q " +
            "from Question q " +
            "where q.targetUser.name like %:targetUserName% " +
            "order by q.lastModifiedDate desc ")
    Page<Question> findByTargetUserName(Pageable pageable, @Param("targetUserName") String targetUserName);
}