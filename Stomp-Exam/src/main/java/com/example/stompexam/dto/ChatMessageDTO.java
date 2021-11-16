package com.example.stompexam.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by bangjinhyuk on 2021/11/16.
 */
@Getter
@Setter
public class ChatMessageDTO {

    private String roomId;
    private String writer;
    private String message;
}