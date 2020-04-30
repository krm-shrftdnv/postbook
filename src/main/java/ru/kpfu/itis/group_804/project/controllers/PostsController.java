package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.PostDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.dto.WrittenPostDto;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.PostsService;
import ru.kpfu.itis.group_804.project.service.UsersService;

import java.util.List;

@Controller
public class PostsController {

    @Autowired
    UsersService usersService;

    @Autowired
    PostsService postsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/id{user-id}/posts")
    public String getUserPosts(@PathVariable("user-id")Long userId, Model model, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        UserDto user = UserDto.from(usersService.getUserById(userId));
        model.addAttribute("user", user);
        List<PostDto> posts = PostDto.from(postsService.getAllPosts(userId));
        model.addAttribute("posts", posts);
        return "posts";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post{post-id}/likers")
    public String getPostLikers(@PathVariable("post-id")Long postId,Model model, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        List<UserDto>likers = UserDto.from(postsService.getLikers(postId));
        model.addAttribute("likers", likers);
        return "likers";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post{post-id}/like")
    public String likePost(@PathVariable("post-id")Long postId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        postsService.likePost(postId, current.getId());
        //???
        return "redirect:/post{post-id}/likers";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/newPost")
    public String newPost(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        return "new_post";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/newPost")
    public String postNewPost(WrittenPostDto postDto, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        postsService.addNewPost(current.getId(), postDto);
        return "redirect:/profile/id" + current.getId() + "/posts";
    }

}
