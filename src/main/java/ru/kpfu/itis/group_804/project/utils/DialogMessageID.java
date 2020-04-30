package ru.kpfu.itis.group_804.project.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kpfu.itis.group_804.project.models.Dialog;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DialogMessageID implements Serializable {
    private Long message_id;
    private Dialog dialog;
}
