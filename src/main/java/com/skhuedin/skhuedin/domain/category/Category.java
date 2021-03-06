package com.skhuedin.skhuedin.domain.category;

import com.skhuedin.skhuedin.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    private Long weight;

    @Builder
    public Category(String name, Long weight) {
        this.name = name;
        this.weight = weight;
    }

    public void updateCategory(Category category) {
        this.name = category.name;
        this.weight = category.weight;
    }

    public void addWeight() {
        this.weight++;
    }

    public void subtractWeight() {
        this.weight--;
    }
}