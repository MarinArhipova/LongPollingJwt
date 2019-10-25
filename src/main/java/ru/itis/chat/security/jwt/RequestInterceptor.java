//package ru.itis.chat.security.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import ru.itis.chat.utils.JwtTokenUtil;
//
//import java.io.IOException;
//import java.util.Collections;
//
//public class RequestInterceptor implements ClientHttpRequestInterceptor {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes,
//                                        ClientHttpRequestExecution execution) throws IOException {
//        ClientHttpResponse response = execution.execute(request, bytes);
//        if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//            UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//            request.getHeaders().replace("Authorization", Collections.singletonList(jwtTokenUtil.generateToken(details)));
//            return execution.execute(request, bytes);
//        }
//        return response;
//    }
//}

