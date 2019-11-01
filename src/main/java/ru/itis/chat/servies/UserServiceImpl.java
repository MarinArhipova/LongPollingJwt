package ru.itis.chat.servies;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.mapper.MessageMapper;
import ru.itis.chat.mapper.UserMapper;
import ru.itis.chat.model.User;
import ru.itis.chat.repositories.UsersRepository;
import ru.itis.chat.security.details.UserDetailsImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.naming.SelectorContext.prefix;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.prefix}")
    private String prefix;

    @Override
    public void signUp(UserForm userForm) {
//        String hashPassword = passwordEncoder.encode(userForm.getPassword());
//        User user = User.builder()
//                .username(userForm.getUsername())
//                .password(hashPassword)
//                .userRole(UserRole.USER)
//                .build();
//        usersRepository.save(user);
        if (!usersRepository.existsByUsername(userForm.getUsername())){
            userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
            User user = userMapper.convertCreateFormToModel(userForm);
            usersRepository.save(user);
        }
    }

    @Override
    public TokenDto signIn(UserForm userForm){
        User user = usersRepository.findByUsername(userForm.getUsername()).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(userForm.getPassword(), user.getPassword())){
            return TokenDto.builder().token(getTokenAsString(user)).build();
        } else {
            throw new BadCredentialsException("Incorrect login/password");
        }
    }

    private String getTokenAsString(User user) {
        return prefix + " " + Jwts.builder()
                .claim("rol", user.getUserRole().toString())
                .claim("username", user.getUsername())
                .setSubject(user.getId().toString())
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes()).compact();
    }

//    @Override
//    public User getCurrentUser(Authentication authentication) {
//        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
//    }

    @Override
    public User getCurrentUser(Authentication authentication) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserDetailsImpl)authentication.getDetails()).getUser().getId();
        return usersRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
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
