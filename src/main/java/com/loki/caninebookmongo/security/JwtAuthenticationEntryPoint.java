package com.loki.caninebookmongo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.loki.caninebookmongo.web.dto.ValidationFailedResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        // Here you can place any message you want

        //Checking if the user had any token
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse();
        Map<String, String> errors = new HashMap<>();
        String authorizationHeader = request.getHeader("Authorization");
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            errors.put("token", "Security token not provided");
            validationFailedResponse.setErrors(errors);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(validationFailedResponse));

        } else {
            errors.put("token", "Invalid Token or Expired");
            validationFailedResponse.setErrors(errors);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(validationFailedResponse));
        }




    }
}
