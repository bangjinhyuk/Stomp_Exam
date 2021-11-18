package com.example.stompexam.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by bangjinhyuk on 2021/11/18.
 */
@Entity
@Getter
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String RoomId;

    @NotBlank
    private Enum<ChatType> chatTypeEnum;

    @NotNull
    private Long sendUser;

    @NotBlank
    private String message;

    @NotNull
    private boolean read;

}
