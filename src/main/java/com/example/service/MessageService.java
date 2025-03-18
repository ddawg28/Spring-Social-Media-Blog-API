package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Component
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank()) {
            return null;
        }
        if (message.getMessageText().length() > 255) {
            return null;
        }
        if (!accountRepository.findById(message.getPostedBy()).isPresent()) {
            return null;
        }
        return messageRepository.save(message);
    }
}
