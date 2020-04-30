package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.DialogsRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DialogServiceImpl implements DialogService {
    @Autowired
    DialogsRepository dialogsRepository;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<DialogDto> getAllDialogs(Long id) {
        //костыль
        List<Dialog> dialogs = dialogsRepository.findAllByMember1OrMember2(id, id);
        return DialogDto.from(dialogs);
    }

    @Override
    public Long createDialog(Long writer_id, Long goal_id) {
        User writer = usersRepository.findById(writer_id).get();
        User goal = usersRepository.findById(goal_id).get();
        Dialog dialog = Dialog.builder()
                .member1(writer)
                .member2(goal)
                .dialog_datetime(LocalDateTime.now())
                .build();
        dialogsRepository.save(dialog);
        Dialog createdDialog = dialogsRepository.findDialogByMember1AndMember2(writer_id, goal_id);
        return createdDialog.getId();
    }

    @Override
    public Long getDialog(Long current, Long friend) {
        if (dialogsRepository.findDialogByMember1AndMember2(current, friend) != null) {
            return dialogsRepository.findDialogByMember1AndMember2(current, friend).getId();
        } else
            return dialogsRepository.findDialogByMember1AndMember2(friend, current).getId();
    }
}
