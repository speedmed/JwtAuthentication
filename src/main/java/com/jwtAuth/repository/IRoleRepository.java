/**
 * 
 */
package com.jwtAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.model.Role;

/**
 * @author Med
 * 3 sept. 2018
 */
public interface IRoleRepository extends JpaRepository<Role, Integer>{

}
