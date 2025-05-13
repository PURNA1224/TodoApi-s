package com.talentXp.todoApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.AuthoritiesEntity;


@Repository
public interface AuthoritiesRepository extends CrudRepository<AuthoritiesEntity, Long>{
	AuthoritiesEntity findByName(String name);
}
