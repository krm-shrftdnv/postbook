package ru.kpfu.itis.group_804.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dialog")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dialog_datetime;

    @ManyToOne
    @JoinColumn(name = "member1", referencedColumnName = "id")
    private User member1;

    @ManyToOne
    @JoinColumn(name = "member2", referencedColumnName = "id")
    private User member2;

    @OneToMany(mappedBy = "dialog")
    private Set<Message> messages;

}
