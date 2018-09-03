/**
 * 
 */
package com.jwtAuth.service;

import com.jwtAuth.model.Role;

/**
 * @author Med
 * 03 sep. 2018
 */
public interface IRoleService {

	public Role create(Role role);
	public Role read(Integer id);
	public Role update(Role role);
	public boolean delete(Integer id);
}
