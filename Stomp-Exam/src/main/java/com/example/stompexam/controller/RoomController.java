package com.example.stompexam.controller;

import com.example.stompexam.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by bangjinhyuk on 2021/11/16.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomRepository repository;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ResponseEntity<String> rooms(){

        log.info("# All Chat Rooms");
        log.info("{}",repository.findAllRooms().toString());

        return ResponseEntity.ok(repository.findAllRooms().toString());
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ResponseEntity<String> create(@RequestParam String name){

        log.info("# Create Chat Room , name: " + name);
         repository.createChatRoomDTO(name);
        return ResponseEntity.ok("success");
    }

    //채팅방 조회
    @GetMapping("/room")
    public ResponseEntity<String> getRoom(String roomId){

        log.info("# get Chat Room, roomID : " + roomId);

        return ResponseEntity.ok(repository.findRoomById(roomId).toString());

    }
}
