package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;

import java.util.List;

public interface DialogsRepository extends JpaRepository<Dialog, Long> {
    List<Dialog> findAllByMember1OrMember2(Long member1, Long member2);
    Dialog findDialogByMember1AndMember2(Long member1, Long member2);
}
