package ru.itis.chat.security.details;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.chat.model.User;
import ru.itis.chat.servies.UserService;

import java.util.ArrayList;

@Service(value = "customUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        if (!userService.findByUsername(value).isPresent()) {
            throw new UsernameNotFoundException("User not found with login: " + value);
        }
        User user = userService.findByUsername(value).get();

//        if (user == null) {
//            throw new UsernameNotFoundException("User with username: " + value + " not found");
//        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }
}
