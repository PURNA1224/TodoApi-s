package com.talentXp.todoApplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.talentXp.todoApplication.entity.UserEntity;

import jakarta.transaction.Transactional;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long>{
	
	@Transactional
	void deleteByUserId(String userId);
	
	UserEntity getByUserId(String userId);

	UserEntity findByUserId(String userId);

	@Transactional
	UserEntity findByEmail(String email);

}
