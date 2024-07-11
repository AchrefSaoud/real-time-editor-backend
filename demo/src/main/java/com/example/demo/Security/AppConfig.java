package com.example.demo.Security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AppConfig {

    private final UserService userService;

    public AppConfig(@Lazy UserService userService){
        this.userService=userService;
    }
    
    @Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService UserDetailsService()throws UsernameNotFoundException {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity u=userService.loadByUsername(username);
                
                Collection<GrantedAuthority> authorities=new ArrayList<>();
                return new User(u.getEmail(), u.getPassword(), authorities);
            }
        };
    }
}
