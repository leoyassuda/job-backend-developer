package com.yassuda.intelipost;

import com.yassuda.intelipost.model.Role;
import com.yassuda.intelipost.model.RoleName;
import com.yassuda.intelipost.model.User;
import com.yassuda.intelipost.repository.RoleRepository;
import com.yassuda.intelipost.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void persistUserRepositoryTest() {

        User user = new User("User-Test-Repo", "usertest",
                "usertest@fakemail.com", "pass#123");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        User result = userRepository.findByUsername("usertest").get();
        assertThat(result.getName()).isEqualTo("User-Test-Repo");
        assertThat(result.getEmail()).isEqualTo("usertest@fakemail.com");
    }

    @Test
    public void updateUserRepositoryTest() {

        User user = new User("User-Test-Update", "usertestupdate",
                "usertestupdate@fakemail.com", "pass#123");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        User result = userRepository.findByUsername("usertestupdate").get();
        assertThat(result.getName()).isEqualTo("User-Test-Update");
        assertThat(result.getEmail()).isEqualTo("usertestupdate@fakemail.com");

        result.setName("User-Test-Repo-Updated");
        userRepository.save(result);

        User checkUpdatedUser = userRepository.findByUsername("usertestupdate").get();
        assertThat(checkUpdatedUser.getName()).isEqualTo("User-Test-Repo-Updated");

    }

    @Test
    public void deleteUserRepositoryTest() {

        User user = new User("User-Test-Delete", "usertestdelete",
                "usertestdelete@fakemail.com", "pass#123");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        User result = userRepository.findByUsername("usertestdelete").get();
        assertThat(result.getName()).isEqualTo("User-Test-Delete");

        User userDelete = userRepository.findByEmail("usertestdelete@fakemail.com").get();
        userRepository.delete(userDelete);

        Assert.assertFalse(userRepository.findByUsername("usertestdelete").isPresent());

    }
}
