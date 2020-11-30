package com.svf.fpl.edu.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

//@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "bot_users")
public class BotUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "user_name")
    @NotEmpty(message = "*Please provide a user name")
    private String userName;
    @Column(name = "allowed_receive")
    private boolean allowedToReceiveMessage;

    @Override
    public String toString() {
        return "BotUser{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", userName='" + userName + '\'' +
                ", allowedToReceiveMessage=" + allowedToReceiveMessage +
                '}';
    }
}
