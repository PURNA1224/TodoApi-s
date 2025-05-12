package com.talentXp.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.AuthoritiesEntity;


@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Long>{
	AuthoritiesEntity findByName(String name);
}
