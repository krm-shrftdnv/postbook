package ru.kpfu.itis.group_804.project.service;

import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;

import java.util.List;

public interface DialogService {
    List<DialogDto> getAllDialogs(Long id);
    Long createDialog(Long writer, Long goal);
    Long getDialog(Long current, Long friend);
}
