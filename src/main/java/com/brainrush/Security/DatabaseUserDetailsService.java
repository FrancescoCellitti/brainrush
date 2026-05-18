package com.brainrush.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brainrush.Repository.UserRepository;
import com.brainrush.model.User;

@Service
public class DatabaseUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> userOpt = userRepository.findByUsername(username);
       if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("ther are no users available with username: " + username);
        }
        return new DatabaseUserDetails(userOpt.get());
    }
    
}
