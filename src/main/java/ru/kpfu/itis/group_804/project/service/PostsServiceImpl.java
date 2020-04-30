package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.dto.WrittenPostDto;
import ru.kpfu.itis.group_804.project.models.Post;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.PostsRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    PostsRepository postsRepository;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<Post> getAllPosts(Long userId) {
        User writer = usersRepository.findById(userId).get();
        return postsRepository.findAllByWriter(writer);
    }

    @Override
    public void addNewPost(Long writerId, WrittenPostDto form) {
        User writer = usersRepository.findById(writerId).get();
        Post post = Post.builder()
                .writer(writer)
                .text(form.getText())
                .postDateTime(LocalDateTime.now())
                .build();
        postsRepository.save(post);
    }

    @Transactional
    @Override
    public void likePost(Long postId, Long likerId) {
        User liker = usersRepository.findById(likerId).get();
        Post post = postsRepository.findById(postId).get();
        post.getLikers().add(liker);
        liker.getFavorites().add(post);
        postsRepository.save(post);
        usersRepository.save(liker);
    }

    @Transactional
    @Override
    public List<User> getLikers(Long postId) {
        Post post = postsRepository.findById(postId).get();
        return new ArrayList<>(post.getLikers());
    }

}
