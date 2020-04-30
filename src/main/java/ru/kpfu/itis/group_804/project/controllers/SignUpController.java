package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.group_804.project.dto.SignUpDto;
import ru.kpfu.itis.group_804.project.service.SignUpService;
import ru.kpfu.itis.group_804.project.service.exceptions.DuplicateEntryException;
import ru.kpfu.itis.group_804.project.service.exceptions.IncorrectEntryException;

@Controller
public class SignUpController {
    @Autowired
    private SignUpService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public String getSignUpPage(Authentication authentication) {
        if (authentication == null) {
            System.out.println("not authenticated");
            return "sign_up";
        } else {
            return "redirect:/";
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String signUp(SignUpDto form, Model model) {
        try {
            System.out.println(form.toString());
            service.signUp(form);
            return "redirect:/signIn";
        } catch (IncorrectEntryException | DuplicateEntryException e) {
            System.out.println("some exception");
            model.addAttribute("error", e.getMessage());
//            model.addAttribute("nickname",form.getNickname());
//            model.addAttribute("email", form.getEmail());
//            model.addAttribute("birthday", form.getBirthday());
            return "redirect:/signUp";
        }
    }
}
