package com.games.dicegame.model.security;


import com.games.dicegame.model.filter.CustomAuthenticationFilter;
import com.games.dicegame.model.filter.CustomAuthorizationFilter;
import com.games.dicegame.model.service.UserServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserServiceImp userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * We tell Spring how we want to do the authentication.
     * We'll use the class UserDetailService and PasswordEncoder to verify the users save in our database
     *
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        //customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable(); // Disable a Cross Site Request Forgery
        http.sessionManagement().sessionCreationPolicy(STATELESS); // No session will be created or used by Spring Security.
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"/api/players").permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterAfter(new CustomAuthorizationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class); // We put the filter and the filter before of this.
        http.authorizeRequests().antMatchers(POST, "/api/players/{id}/games/**").access("@userSecurity.hasId(authentication,#id)"); // Give access t
        http.authorizeRequests().antMatchers(DELETE, "/api/players/{id}/games/**").access("@userSecurity.hasId(authentication,#id)");
        http.authorizeRequests().antMatchers(GET, "/api/players/{id}/games/**").access("@userSecurity.hasId(authentication,#id)"); // Give access t
        http.authorizeRequests().antMatchers(POST, "/api/players").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
