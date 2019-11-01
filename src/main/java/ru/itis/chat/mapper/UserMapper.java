package ru.itis.chat.mapper;

import org.springframework.stereotype.Component;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.model.User;
import ru.itis.chat.model.UserRole;

@Component
public class UserMapper {
    public User convertCreateFormToModel (UserForm userCreateForm) {
        return User.builder()
                .username(userCreateForm.getUsername())
                .password(userCreateForm.getPassword())
                .userRole(UserRole.USER)
                .build();

    }
}
