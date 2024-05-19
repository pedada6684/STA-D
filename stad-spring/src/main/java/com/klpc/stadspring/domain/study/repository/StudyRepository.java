package com.klpc.stadspring.domain.study.repository;

import com.klpc.stadspring.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study,Long> {
}
