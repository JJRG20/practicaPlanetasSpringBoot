package com.example.sistemaplanetas.security;

import com.example.sistemaplanetas.entity.User;
import com.example.sistemaplanetas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== DEBUG: Loading user: " + username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("=== DEBUG: User not found: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });
        
        System.out.println("=== DEBUG: User found: " + user.getUsername());
        System.out.println("=== DEBUG: Role: " + user.getRole());
        System.out.println("=== DEBUG: Password length: " + user.getPassword().length());
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}