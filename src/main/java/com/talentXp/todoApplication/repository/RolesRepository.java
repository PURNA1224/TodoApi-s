package com.talentXp.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.RoleEntity;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByName(String name);

}
