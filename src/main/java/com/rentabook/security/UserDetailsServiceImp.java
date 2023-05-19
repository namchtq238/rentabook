package com.rentabook.security;


import com.rentabook.domain.User;
import com.rentabook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("NOT FOUND");
        }
        return new CustomUserDetails(user);
    }
}