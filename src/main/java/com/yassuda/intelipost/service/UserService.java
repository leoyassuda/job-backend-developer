package com.yassuda.intelipost.service;

import com.yassuda.intelipost.exception.ResourceNotFoundException;
import com.yassuda.intelipost.model.Role;
import com.yassuda.intelipost.model.RoleName;
import com.yassuda.intelipost.model.User;
import com.yassuda.intelipost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public Boolean checkExistsUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean checkExistsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName(RoleName.ROLE_USER);

        user.setRoles(Collections.singleton(userRole));

        return this.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
