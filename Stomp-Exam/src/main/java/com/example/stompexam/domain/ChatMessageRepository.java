package com.example.stompexam.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bangjinhyuk on 2021/11/18.
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
