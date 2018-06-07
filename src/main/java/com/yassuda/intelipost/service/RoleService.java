package com.yassuda.intelipost.service;

import com.yassuda.intelipost.exception.ApplicationException;
import com.yassuda.intelipost.model.Role;
import com.yassuda.intelipost.model.RoleName;
import com.yassuda.intelipost.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApplicationException("User Role not set."));
    }
}
