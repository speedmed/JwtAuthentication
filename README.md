# Jwt Authentication
Stateless Authentication using JWT (JSON Web Token) with Spring Boot.

# Explanation Link

You will find the full explanation to implement the project on my Blog.

http://www.ninjadevcorner.com/2018/09/stateless-authentication-jwt-secure-spring-boot-rest-api.html

https://ninjadevcorner.blogspot.com/2018/09/stateless-authentication-jwt-secure-spring-boot-rest-api.html


# Stateless authentication:

What Stateless authentication means is that we don’t want to store any information about user on the server. One of the reasons can be serving your application through a load balancer and we do not want to use sticky sessions.  With this configuration in place the user can reach out different backend servers transparently without having the need to reauthenticate. For REST API also, this may sound a better approach because we don’t want to rely on Basic Auth.
The basic idea behind a stateless authentication is that the user authenticates against your application, if the operation is successful the server will respond with a token that the user should send on any request using an Http Header or a Cookie to prove his identity. On the other side the server also needs to validate our token and verify if still valid, not expired and nobody tampered with it.
Exactly this can be achieved by using JWT.
