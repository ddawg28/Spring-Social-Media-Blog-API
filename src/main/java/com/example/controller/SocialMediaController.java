package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.service.*;

import java.security.DrbgParameters.Reseed;
import java.util.List;

import javax.websocket.server.PathParam;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerUser(@RequestBody Account account) {
        if (accountService.findAccount(account) == true) {
            return ResponseEntity.status(409).build();
        }
        Account res = accountService.createAccount(account);
        if (res != null) {
            return ResponseEntity.status(200).body(account);
        }
        
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginUser(@RequestBody Account account) {
        Account result = accountService.login(account);
        if (result == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message result = messageService.createMessage(message);
        if (result == null) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        List<Message> result = messageService.getAllMessages();
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessage(@PathVariable Integer messageId) {
        Message result = messageService.getMessage(messageId);
        return ResponseEntity.status(200).body(result);
    }

    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        Integer result = messageService.deleteMessage(messageId);
        if (result == 1) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@RequestBody Message messageText, @PathVariable Integer messageId) {
        Integer result = messageService.updateMessage(messageText.getMessageText(), messageId);
        if (result == 1) {
            return ResponseEntity.status(200).body(result);
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId) {
        List<Message> result = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.status(200).body(result);
    }
}
