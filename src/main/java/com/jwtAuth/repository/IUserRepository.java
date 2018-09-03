/**
 * 
 */
package com.jwtAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.model.User;

/**
 * @author Med
 * 3 sept. 2018
 */
public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
