package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.dto.MessageDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.repository.DialogsRepository;
import ru.kpfu.itis.group_804.project.service.MessagesService;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    MessagesService messagesService;
    @Autowired
    DialogsRepository dialogsRepository;

    @GetMapping("/chat{dialog-id}")
    public String getChatWithUser(@PathVariable("dialog-id") Long dialogId, Model model, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        List<Message> messages = messagesService.getLastNMessagesFromX(dialogId, 0, 50);
        List<MessageDto> messagesDto = MessageDto.from(messages);
        model.addAttribute("messages", messagesDto);
        Dialog dialog = dialogsRepository.findById(dialogId).get();
        DialogDto dialogDto = DialogDto.from(dialog);
        model.addAttribute("dialog", dialogDto);
        UserDto other = UserDto.from((
                dialog.getMember1()
                        .getId()
                        .equals(current.getId())) ? dialog.getMember2() : dialog.getMember1()
        );
        model.addAttribute("user", other);
        return "chat";
    }


}
