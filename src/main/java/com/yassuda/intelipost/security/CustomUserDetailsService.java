package com.yassuda.intelipost.security;

import com.yassuda.intelipost.exception.ResourceNotFoundException;
import com.yassuda.intelipost.model.User;
import com.yassuda.intelipost.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
            return UserPrincipal.create(user);
        } catch (NoSuchElementException e) {
            logger.error("User not found with username or email : " + usernameOrEmail);
            throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
        }
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
