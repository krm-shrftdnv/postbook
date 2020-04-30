package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.FriendsService;
import ru.kpfu.itis.group_804.project.service.UsersService;

import java.util.List;

@Controller
public class FriendsController {

    @Autowired
    FriendsService friendsService;
    @Autowired
    UsersService usersService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends")
    public String getUserFriends(@RequestParam("id") Long id, Model model, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        if (!current.getId().equals(id)) {
            UserDto user = UserDto.from(usersService.getUserById(id));
            model.addAttribute("user", user);
        } else model.addAttribute("user", current);
        List<UserDto> friends = friendsService.getAllFriends(id);
        if (friends.isEmpty()) model.addAttribute("noFriends", true);
        model.addAttribute("friends", friends);
        model.addAttribute("current", current);
        return "friends";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends/getNewFriends")
    public String getNewFriends(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        List<UserDto> users = friendsService.getNonFriends(current.getId());
        model.addAttribute("users", users);
        model.addAttribute("current", current);
        return "non-friends";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends/toConfirm")
    public String friendsToConfirm(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        List<UserDto> users = friendsService.getIncomingRequests(current.getId());
        model.addAttribute("users", users);
        model.addAttribute("current", current);
        return "subscribers";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends/sentRequests")
    public String getSentRequests(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        List<UserDto> users = friendsService.getOutgoingRequests(current.getId());
        model.addAttribute("users", users);
        model.addAttribute("current", current);
        return "subscribes";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/startFriendship")
    public String startFriendship(@RequestParam("receiver-id") Long receiver, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        friendsService.addToFriends(current.getId(), receiver);
        return "redirect:/profile/id" + receiver;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/confirmFriendship")
    public String confirmFriendship(@RequestParam("sender-id") Long sender, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        friendsService.confirmFriend(current.getId(), sender);
        return "redirect:/friends?id=" + current.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/breakFriendship")
    public String breakFriendship(@RequestParam("friend-id") Long friend, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        friendsService.breakFriendship(current.getId(), friend);
        return "redirect:/friends?id=" + current.getId();
    }

}
