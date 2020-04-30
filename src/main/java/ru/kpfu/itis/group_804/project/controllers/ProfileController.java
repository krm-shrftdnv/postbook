package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.FriendsService;
import ru.kpfu.itis.group_804.project.service.UsersService;

import javax.transaction.Transactional;

@Controller
public class ProfileController {

    @Autowired
    UsersService usersService;
    @Autowired
    FriendsService friendsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/id{id}")
    public String getProfile(Authentication authentication, Model model, @PathVariable("id") Long id) {
        if (usersService.userExists(id)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserDto current = UserDto.from(userDetails.getUser());
            model.addAttribute("current", current);
            UserDto user = UserDto.from(usersService.getUserById(id));
            model.addAttribute("user", user);
            if (friendsService.areFriends(user.getId(), current.getId()) || (user.getId().equals(current.getId()))) model.addAttribute("visiablePosts", true);
            return "profile";
        } else return "redirect:/profile/error";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getCurrentProfile(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        return "redirect:/profile/id" + current.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/error")
    public String getNonexistentProfile() {
        return "profile_error";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("profile/updateInformation")
    public String updateInformation(Authentication authentication, Model model){
        return "update_user";
    }


}
