package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.servies.UserService;

@RestController
public class SignInController {
    @Autowired
    UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> signIn(@RequestBody UserForm userForm){
        return ResponseEntity.ok().body(userService.signIn(userForm));
    }
}
