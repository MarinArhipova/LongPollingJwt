package ru.itis.chat.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.chat.security.details.UserDetailsImpl;

@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {


    @Value("${jwt.secret}")
    private String jwtSecret;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthentication jwtTokenAuthentication = (JwtTokenAuthentication) authentication;
        Claims body;

        try {
            body = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(jwtTokenAuthentication.getName())
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationServiceException("Invalid token");
        }
        UserDetails userDetails = new UserDetailsImpl(
                Long.parseLong(body.get("sub").toString()),
                body.get("rol").toString(),
                body.get("username").toString()
        );

        jwtTokenAuthentication.setUserDetails(userDetails);
        jwtTokenAuthentication.setAuthenticated(true);


        return jwtTokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtTokenAuthentication.class.equals(authentication);
    }
}
