package com.bootcamp.usermanager.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bootcamp.usermanager.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhoneNumber(String phoneNumber);

	Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);
	
	Optional<User> findByResetToken(String token);
	
	@Transactional
	@Modifying
	@Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.id = ?1")
	void updateLastLogin(Integer id);
	
	@Transactional
	@Modifying
	@Query("update User u set u.resetToken = ?1, u.tokenExpirationDate = CURRENT_TIMESTAMP")
	void updateResetToken(String token);

	@Transactional
	@Modifying
	@Query("update User u set u.password = ?2 where u.id = ?1")
	void updatePassword(Integer id, String password);
	
}
