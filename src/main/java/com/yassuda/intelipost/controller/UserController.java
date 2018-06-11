package com.yassuda.intelipost.controller;

import com.yassuda.intelipost.model.User;
import com.yassuda.intelipost.payload.UserIdentityAvailability;
import com.yassuda.intelipost.payload.UserProfile;
import com.yassuda.intelipost.payload.UserProfileMinimum;
import com.yassuda.intelipost.security.CurrentUser;
import com.yassuda.intelipost.security.UserPrincipal;
import com.yassuda.intelipost.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    public UserProfileMinimum getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserProfileMinimum(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        logger.info("Checking if username is available ...");
        Boolean isAvailable = !userService.checkExistsUserName(username);
        logger.info("username " + username + " available : " + isAvailable);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        logger.info("Checking if email is available ...");
        Boolean isAvailable = !userService.checkExistsEmail(email);
        logger.info("email " + email + " available : " + isAvailable);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userService.findByUsername(username);
        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
    }

}