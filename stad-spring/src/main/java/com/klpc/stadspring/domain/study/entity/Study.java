package com.klpc.stadspring.domain.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String category;
    String title;

    public static Study addStudy(String category, String title){
        Study study = new Study();
        study.category=category;
        study.title=title;
        return study;
    }
}
