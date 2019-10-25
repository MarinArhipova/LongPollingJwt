package ru.itis.chat.servies;

import org.springframework.security.core.Authentication;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(UserForm userForm);
    User getCurrentUser(Authentication authentication);
    Optional<User> findByUsername(String username);
    List<User> getAll();
}
