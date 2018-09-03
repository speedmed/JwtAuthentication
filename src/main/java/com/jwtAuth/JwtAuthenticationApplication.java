package com.jwtAuth;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jwtAuth.model.Role;
import com.jwtAuth.model.User;
import com.jwtAuth.service.IRoleService;
import com.jwtAuth.service.IUserService;

@SpringBootApplication
public class JwtAuthenticationApplication implements CommandLineRunner{

	@Autowired
	IUserService userService;
	@Autowired
	IRoleService roleService;
	
	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Role role_admin = new Role("ADMIN");
		Role role_user = new Role("USER");
		roleService.create(role_admin);
		roleService.create(role_user);
		
		User admin = new User("admin", "007007", true);
		admin.addRole(role_admin);
		admin.addRole(role_user);
		
		User u = new User("user", "007007", true);
		u.addRole(role_user);
		
		userService.create(admin);
		userService.create(u);
	}
}
