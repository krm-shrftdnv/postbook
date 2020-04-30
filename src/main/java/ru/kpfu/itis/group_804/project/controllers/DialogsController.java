package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.dto.NewDialogDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.DialogService;
import ru.kpfu.itis.group_804.project.service.FriendsService;

import java.util.List;

@Controller
public class DialogsController {

    @Autowired
    DialogService dialogService;
    @Autowired
    FriendsService friendsService;
    @Autowired
    UsersRepository usersRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dialogs")
    public String getDialogs(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        List<DialogDto> dialogs = dialogService.getAllDialogs(current.getId());
        model.addAttribute("dialogs", dialogs);
        return "dialogs";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/dialogs/newDialog")
    public String newDialog(Authentication authentication, @RequestBody NewDialogDto dto){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        Long dialogId = dialogService.createDialog(current.getId(), usersRepository.findUserByNickname(dto.getNickname()).get().getId());

        return "redirect:/chat" + dialogId;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dialogs/getChat")
    public String getChatWithFriend(@RequestParam("dialog-id")Long dialogId){
        return "redirect:/chat" + dialogId;
    }

}
