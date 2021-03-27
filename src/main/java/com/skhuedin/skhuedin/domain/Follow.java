package com.skhuedin.skhuedin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private LocalDateTime create;

    @LastModifiedDate
    private LocalDateTime updated;


    @Builder
    public Follow(Long id, LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.create = create;
        this.updated = updated;
    }


}
