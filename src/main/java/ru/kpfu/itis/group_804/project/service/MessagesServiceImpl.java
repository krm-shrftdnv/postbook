package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.repository.MessageRepository;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> getLastNMessagesFromX(Long dialogId, int n, int x) {
        Page<Message> lastMessagesPage = messageRepository.findAll(PageRequest.of(x,n));
        return lastMessagesPage.getContent();
    }

    @Override
    public void addMessage(Message message) {
           messageRepository.save(message);
    }

}
