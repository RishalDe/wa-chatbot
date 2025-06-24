package com.chatapp.telegramchat.repository;

import com.chatapp.telegramchat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByFromOrTo(String from, String to);
}
