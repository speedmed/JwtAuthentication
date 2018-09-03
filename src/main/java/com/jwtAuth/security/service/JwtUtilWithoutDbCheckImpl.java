package com.jwtAuth.security.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.jwtAuth.security.JwtUser;
import com.jwtAuth.security.controller.dto.JwtAuthenticationResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Med
 * 3 sept. 2018
 */
@Component
public class JwtUtilWithoutDbCheckImpl implements JwtUtil, Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_ID = "jti";
    static final String CLAIM_KEY_USERID = "userId";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ROLES = "roles";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public String getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
        	id = null;
        }
        return id;
    }
    
    @Override
    public Long getUserIdFromToken(String token){
    	Long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(claims.get(CLAIM_KEY_USERID).toString());
        } catch (Exception e) {
        	userId = null;
        }
        return userId;
    }
    
    @Override
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
    	List<String> roles;
        try {
            final Claims claims = getClaimsFromToken(token);
            roles = (List<String>)claims.get(CLAIM_KEY_ROLES);
        } catch (Exception e) {
        	roles = null;
        }
        return roles != null ? roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()) : null;
    }
    
    @Override
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }
    
    @Override
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    @Override
    public Claims getClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException, 
    MalformedJwtException, SignatureException, IllegalArgumentException
{
        Claims claims;
        
        claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

        return claims;
    }
    
    
    private Date generateExpirationDate(String type) {
    	if(type.equals("token")){
    		return new Date(System.currentTimeMillis() + expiration * 1000);
    	}else{
    		return new Date(System.currentTimeMillis() + expiration * 5 * 1000);
    	}
    }
    
    @Override
    public Boolean isTokenExpired(String token) {
    	try{
    		getClaimsFromToken(token);
    		return false;
    	}catch(ExpiredJwtException ex){
    		return true;
    	}
    	
    }
    
    @Override
    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    
    @Override
    public JwtAuthenticationResponse generateToken(String username) {
    	
        Map<String, Object> claims = generateClaims(username);
        String token = doGenerateToken(claims);
        String refreshToken = doGenerateRefreshToken(claims);
        return new JwtAuthenticationResponse(token, refreshToken);
    }
    
    @Override
    public JwtAuthenticationResponse refreshToken(String username) {
    	
    	String newToken;
        String newRefreshToken;
        try {
            final Map<String, Object> claims = generateClaims(username);
            newToken = doGenerateToken(claims);
            newRefreshToken = doGenerateRefreshToken(claims);
        } catch (Exception e) {
        	newToken = null;
        	newRefreshToken = null;
        }
        return new JwtAuthenticationResponse(newToken, newRefreshToken);
    }
    
    private Map<String, Object> generateClaims(String username){
    	final JwtUser userDetails = (JwtUser)userDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        
        claims.put(CLAIM_KEY_ID, UUID.randomUUID().toString());
        claims.put(CLAIM_KEY_USERID, userDetails.getId());
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLES, AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));
        
        return claims;
    }
    
    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder()
        		.setClaims(claims)
                .setExpiration(generateExpirationDate("token"))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    private String doGenerateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
        		.setClaims(claims)
                .setExpiration(generateExpirationDate("refresh"))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    @Override
    public Boolean validateToken(String token) {
    	try{
    		getClaimsFromToken(token);
    		return true;
    	}catch(Exception ex){
    		return false;
    	}
    }
}
