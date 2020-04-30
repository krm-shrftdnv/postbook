package ru.kpfu.itis.group_804.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.group_804.project.models.enums.Role;
import ru.kpfu.itis.group_804.project.models.enums.State;
import ru.kpfu.itis.group_804.project.models.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private Date birthday;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "sender")
    private Set<FriendshipRequest> friendshipsWhereIsSender;

    @OneToMany(mappedBy = "receiver")
    private Set<FriendshipRequest> friendshipsWhereIsReceiver;

    @OneToOne(mappedBy = "user")
    private UserEmailValidity userEmailValidity;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_friend_user",
            joinColumns = {@JoinColumn(name = "user_id_1")},
            inverseJoinColumns = {@JoinColumn(name = "user_id_2")}
    )
    private Set<User>  friends = new HashSet<>();

    @OneToMany(mappedBy = "writer")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "likers")
    private Set<Post> favorites = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        List<Long> friendsIds = new ArrayList<>();
        for(User user : this.getFriends()){
            friendsIds.add(user.getId());
        }
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", state=" + state +
                ", role=" + role +
                ", friends" + friendsIds.toString() +
                '}';
    }

    @OneToMany(mappedBy = "member1")
    private Set<Dialog> dialogsWhereIsMember1;

    @OneToMany(mappedBy = "member2")
    private Set<Dialog> dialogsWhereIsMember2;

    @OneToMany(mappedBy = "user_from")
    private Set<Message> messagesWhereIsUserFrom;

    @OneToMany(mappedBy = "user_to")
    private Set<Message> messagesWhereIsUserTo;
}
