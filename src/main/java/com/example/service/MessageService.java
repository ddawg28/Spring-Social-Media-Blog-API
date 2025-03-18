package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

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

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessage(Integer id) {
        Optional<Message> result = messageRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public Integer deleteMessage(Integer id) {
        if (messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
    
    public Integer updateMessage(String text, Integer id) {
        if (text.isBlank()) {
            return 0;
        }
        if (text.length() > 255) {
            return 0;
        }
        Optional<Message> msg = messageRepository.findById(id);
        if (msg.isPresent()) {
            Message newMsg = msg.get();
            newMsg.setMessageText(text);
            messageRepository.save(newMsg);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByUser(Integer id) {
        return messageRepository.findAllByPostedBy(id);
    }
}
