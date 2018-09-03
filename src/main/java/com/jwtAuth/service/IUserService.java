package com.jwtAuth.service;

import com.jwtAuth.model.User;

/**
 * @author Med
 * 03 sep. 2018
 */
public interface IUserService {
	
	public User findUserByUsername(String username);
	public User create(User u);
	public User read(Long id);
	public User update(User u);
	public void delete(Long id);

}
