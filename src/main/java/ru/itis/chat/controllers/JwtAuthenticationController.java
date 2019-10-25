package ru.itis.chat.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.chat.forms.UserForm;
import ru.itis.chat.security.details.JwtUserDetailsService;
import ru.itis.chat.security.jwt.JwtResponse;
import ru.itis.chat.utils.JwtTokenUtil;



@RestController
@CrossOrigin
public class JwtAuthenticationController{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserForm userForm) throws Exception {
        authenticate(userForm.getUsername(), userForm.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userForm.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
