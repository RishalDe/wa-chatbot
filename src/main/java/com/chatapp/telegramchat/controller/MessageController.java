package com.chatapp.telegramchat.controller;

import com.chatapp.telegramchat.model.Message;
import com.chatapp.telegramchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*") // allow frontend to call backend from browser
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // Set timestamp before saving
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return messageRepository.save(message);
    }
}
