/**
 * 
 */
package com.jwtAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.model.User;

/**
 * @author Med
 * 03 sep. 2017
 */
public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
