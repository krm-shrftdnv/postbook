package ru.kpfu.itis.group_804.project.service;

import ru.kpfu.itis.group_804.project.dto.WrittenPostDto;
import ru.kpfu.itis.group_804.project.models.Post;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;

public interface PostsService {
    List<Post> getAllPosts(Long userId);
    void addNewPost(Long writerId, WrittenPostDto form);
    void likePost(Long postId, Long likerId);
    List<User> getLikers(Long postId);
}
