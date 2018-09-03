package com.jwtAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtAuth.model.User;
import com.jwtAuth.security.service.JwtUtil;
import com.jwtAuth.service.IUserService;

/**
 * @author Med
 * 3 sept. 2018
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthenticatedUserController {

	
	@Value("${jwt.header}")
    private String tokenHeader;
	
    @Autowired
    @Qualifier(value = "jwtUtilWithoutDbCheckImpl")
    private JwtUtil jwtTokenUtil;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public ResponseEntity<String> getAuthenticatedUser(@RequestHeader(value= "${jwt.header}") String token) {
        
    	Long userId = jwtTokenUtil.getUserIdFromToken(token);
    	User user = userService.read(userId);
        return ResponseEntity.ok(user.getUsername());
    }

}
