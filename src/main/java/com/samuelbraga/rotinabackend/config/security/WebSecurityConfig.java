package com.samuelbraga.rotinabackend.config.security;

import com.samuelbraga.rotinabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  private final AuthenticationService authenticationService;
  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserRepository userRepository;
  
  @Autowired
  public WebSecurityConfig(AuthenticationService authenticationService, TokenAuthenticationService tokenAuthenticationService, UserRepository userRepository) {
    this.authenticationService = authenticationService;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.userRepository = userRepository;
  }
  
  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.authenticationService).passwordEncoder(new BCryptPasswordEncoder());
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()

      .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
      .antMatchers(HttpMethod.POST, "/session").permitAll()
         
      .anyRequest().authenticated()
      .and().csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      
      .and()
      .addFilterBefore(new AuthenticationFilter(tokenAuthenticationService, userRepository), UsernamePasswordAuthenticationFilter.class);
  }
}