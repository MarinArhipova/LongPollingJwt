package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.servies.UserService;

@RestController
public class SignUpController {

    @Autowired
    private UserService usersService;

    @PostMapping("/signUp")
    @PreAuthorize("permitAll()")
    public void create(@RequestBody UserForm userForm) {
        usersService.signUp(userForm);
    }
}
