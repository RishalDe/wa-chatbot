package com.chatapp.telegramchat.controller;

import com.chatapp.telegramchat.model.Message;
import com.chatapp.telegramchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*") // allow frontend calls from any origin
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // Set current timestamp before saving
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return messageRepository.save(message);
    }

    // Fixed method with null-safe stream
    @GetMapping("/conversations")
    public List<String> getUniqueConversations() {
        List<Message> messages = messageRepository.findAll();
        String bot = "BOT"; // Adjust if your bot identifier is different

        return messages.stream()
                .flatMap(m -> Stream.of(m.getFrom(), m.getTo())
                        .filter(Objects::nonNull))
                .filter(num -> !num.equals(bot))
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/conversation/{number}")
    public List<Message> getConversationWith(@PathVariable String number) {
        List<Message> all = messageRepository.findAll();

        return all.stream()
                .filter(m -> number.equals(m.getFrom()) || number.equals(m.getTo()))
                .sorted((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
                .collect(Collectors.toList());
    }
}
