package com.talentXp.todoApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.RoleEntity;

@Repository
public interface RolesRepository extends CrudRepository<RoleEntity, Long> {

	RoleEntity findByName(String name);

}
