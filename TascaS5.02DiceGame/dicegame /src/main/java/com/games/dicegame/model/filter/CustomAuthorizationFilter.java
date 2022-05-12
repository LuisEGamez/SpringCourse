package com.games.dicegame.model.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/*
 * OncePerRequestFilter is going to intercept every request that comes in to the application
 * This is what we're going to do when we look in the header, and we see that it's an authorization header,
 * and then we check again we see that it has the word Bearer in front of it, so we know that this is our
 * JWT and that's the only time we're going to do all of this processing
 */

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/api/login")){ // We check if the path comes from the login page
            filterChain.doFilter(request,response);// We're not going to do anything. It's just going to make this filter pass the request to the next filter and the filter chain. Which mean we're not doing anything
        }else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);  // Trying to access the authorization headers. That should be the key for the token.
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String token = authorizationHeader.substring("Bearer ".length()); // Removing the word bearer
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build(); // Creating a verifier to verify the token
                    DecodedJWT decodedJWT = verifier.verify(token); // Decoding token
                    String username = decodedJWT.getSubject(); // We grab the username of the user from the token
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class); // We obtain the roles and save in an array of string.
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role))); // We're passing the array to collection. We need convert into something extend grantedAuthority
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // We have to set this user in the security context holder. We're give the user to Spring Security again
                    filterChain.doFilter(request, response); // We still need to let the request continue its course.
                }catch (Exception e){
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error", e.getMessage()); // We pass the error for the header
                    response.setStatus(FORBIDDEN.value());
                }

            }else {
                filterChain.doFilter(request,response);
            }
        }

    }
}
