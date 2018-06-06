package com.yassuda.intelipost.repository;

import com.yassuda.intelipost.model.Role;
import com.yassuda.intelipost.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}