package com.games.dicegame.model.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.games.dicegame.model.security.UserDetailsCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Attempts to authenticate the passed Authentication object, returning a fully populated Authentication object (including granted authorities) if successful.

    private final AuthenticationManager authenticationManager; // Valid to authenticate the user

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /*
     * Whenever the user tries to log in.
     * We're grabbing the information that is already coming with the request and them pass it in to the
     * username password authentication token and them we call to authenticationManager
     * We tell the authenticationManager to authenticate the user that is logging in here with this request.
     *
     * If the authentication is not successful the method throw a AuthenticationException, but if the authentication is successful,
     * then it's going to call this successful authentication
     *
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("Username is; {}", username);
        log.info("Password is; {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // We're creating a new algorithm to sing the JWT.
        // "Secret" when we are working in production must be secret, encrypt it and them when you load it up
        // you decrypt it ang them pass it in here from some utility class.
        String access_token = JWT.create()
                                    .withSubject(userDetailsCustom.getUsername()) // Subject can be really any string that you so  that can be like the user id or username or something unique
                                                                                    // about the user so that you can identify the user by that specific token
                                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 min to expire the token
                                    .withIssuer(request.getRequestURL().toString()) // We pass the issuer(Emisor) from request
                                    .withClaim("user_id",userDetailsCustom.getId())
                                    .withClaim("roles", userDetailsCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) //We pass in all the claims so all the roles for that specific user
                                    .sign(algorithm); // We sing the token with the algorithm.

        response.setHeader("access_token", access_token);
    }
}
