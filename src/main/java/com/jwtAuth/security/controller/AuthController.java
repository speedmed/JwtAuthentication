/**
 * 
 */
package com.jwtAuth.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtAuth.controller.dto.ServerResponse;
import com.jwtAuth.security.controller.dto.JwtAuthenticationRequest;
import com.jwtAuth.security.controller.dto.JwtAuthenticationResponse;
import com.jwtAuth.security.service.JwtUtil;

/**
 * @author Med
 * 3 sept. 2018
 */
@RestController
@RequestMapping("/api/open/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier(value = "jwtUtilWithoutDbCheckImpl")
    private JwtUtil jwtTokenUtil;
    
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        // Perform the security
    	try{
    		final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    	}catch(AuthenticationException e){
    		
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServerResponse(e.getMessage()));
    	}
        
        final JwtAuthenticationResponse token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
        // Return the token
        return ResponseEntity.ok(token);
    }
}
