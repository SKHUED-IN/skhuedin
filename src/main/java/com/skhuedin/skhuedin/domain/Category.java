package com.skhuedin.skhuedin.domain;

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

    private Integer weight;

    @Builder
    public Category(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    public void updateCategory(Category category) {
        this.name = category.name;
        this.weight = category.weight;
    }
}