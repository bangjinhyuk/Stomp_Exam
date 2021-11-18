package com.example.stompexam.controller;

import com.example.stompexam.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Created by bangjinhyuk on 2021/11/16.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 들어왔습니다.");
        log.info("{} 님이 채팅방에 참여하였습니다.",message.getWriter());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
        log.info("{}:{}",message.getWriter(),message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/left")
    public void left(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 나갔습니다.");
        log.info("{}:{}",message.getWriter(),message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/read")
    public void read(ChatMessageDTO message){
        //TODO: 읽은 마지막 채팅을 어떻게 받을지 고려 필요
        log.info("{}:{}",message.getWriter(),message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/image")
    public void image(ChatMessageDTO message){
        //TODO: 이미지 받아오는것 및 저장 하는 부분 구현 필요
        log.info("{}:{}",message.getWriter(),message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
