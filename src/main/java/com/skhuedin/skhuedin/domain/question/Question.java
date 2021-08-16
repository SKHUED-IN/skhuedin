package com.skhuedin.skhuedin.domain.question;

import com.skhuedin.skhuedin.domain.BaseEntity;
import com.skhuedin.skhuedin.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    private User writerUser;

    private String title;

    private String content;

    private Boolean status; // false => 보이기, true => 숨기기

    private Boolean fix; // false => 상단 고정 x, true => 상단 고정 o

    private Integer view;

    @Builder
    public Question(User targetUser, User writerUser, String title,
                    String content, Boolean status, Boolean fix) {
        this.targetUser = targetUser;
        this.writerUser = writerUser;
        this.title = title;
        this.content = content;

        // TODO status 및 fix 용도 다시 한번 확인
        if (status == null) {
            this.status = false;
        } else {
            this.status = status;
        }
        if (fix == null) {
            this.fix = false;
        } else {
            this.fix = fix;
        }

        this.view = 0;
    }

    public void updateQuestion(Question question) {
        this.targetUser = question.targetUser;
        this.writerUser = question.writerUser;
        this.title = question.title;
        this.content = question.content;
        this.status = question.status;
        this.fix = question.fix;
        // this.view = question.view x => update 시 view는 반영 x 기존 값을 그대로 유지
    }

    public void addView() {
        this.view++;
    }
}