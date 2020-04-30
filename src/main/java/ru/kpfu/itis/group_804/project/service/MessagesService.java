package ru.kpfu.itis.group_804.project.service;

import ru.kpfu.itis.group_804.project.models.Message;

import java.util.List;

public interface MessagesService {

    List<Message> getLastNMessagesFromX(Long dialogId, int n, int x);

    void addMessage(Message message);

}
