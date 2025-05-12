package com.talentXp.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.UserEntity;

import jakarta.transaction.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long>{

	UserEntity getByEmail(String email);

	UserEntity findByEmail(String string);
	
	@Transactional
	void deleteByUserId(String userId);
	
	UserEntity getByUserId(String userId);

	UserEntity findByUserId(String userId);

}
