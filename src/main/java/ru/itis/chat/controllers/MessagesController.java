package ru.itis.chat.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.servies.MessageService;

import java.util.List;

@RestController
public class MessagesController {
    @Autowired
    MessageService messageService;

    private final MessageDto messageDto = new MessageDto();

    @GetMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDto>> receiveMessage(@RequestHeader("Authorization") String auth) throws InterruptedException {
        if (messageDto.getText().isEmpty()) {
            messageDto.wait();
        }
        messageDto.setText(null);
        messageDto.setAuthor(null);
        return ResponseEntity.ok().body(messageService.getAll());
    }

    @SneakyThrows
    @PostMapping("/messages")
    public ResponseEntity<Object> getMessagesForPage(@RequestHeader("Authorization") String auth, @RequestBody MessageDto form) {
        synchronized (messageDto) {
            messageDto.setText(form.getText());
            messageDto.setAuthor(form.getAuthor());
            messageService.add(messageDto);
        }
        return ResponseEntity.ok().build();
       }
    }

