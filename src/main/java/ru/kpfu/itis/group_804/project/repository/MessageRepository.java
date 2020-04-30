package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.utils.DialogMessageID;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, DialogMessageID> {
    List<Message> findAllByDialog(Dialog dialog);
    Page<Message>  findAll(Pageable pageable);
}
