package com.jwtAuth.security.controller.dto;

import java.io.Serializable;

/**
 * @author Med
 * 3 sept. 2018
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String refreshToken;

    public JwtAuthenticationResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return this.token;
    }

	public String getRefreshToken() {
		return refreshToken;
	}
}
