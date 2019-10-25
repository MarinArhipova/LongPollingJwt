package ru.itis.chat.servies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.model.User;
import ru.itis.chat.model.UserRole;
import ru.itis.chat.repositories.UsersRepository;
import ru.itis.chat.security.details.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        User user = User.builder()
                .username(userForm.getUsername())
                .password(hashPassword)
                .userRole(UserRole.USER)
                .build();
        usersRepository.save(user);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }

    @Override
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }


}
