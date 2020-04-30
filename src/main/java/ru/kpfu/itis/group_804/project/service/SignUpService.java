package ru.kpfu.itis.group_804.project.service;

import ru.kpfu.itis.group_804.project.dto.SignUpDto;
import ru.kpfu.itis.group_804.project.service.exceptions.DuplicateEntryException;
import ru.kpfu.itis.group_804.project.service.exceptions.IncorrectEntryException;

public interface SignUpService {
    void signUp(SignUpDto form) throws IncorrectEntryException, DuplicateEntryException;
}
