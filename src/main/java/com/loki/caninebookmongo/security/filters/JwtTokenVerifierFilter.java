package com.loki.caninebookmongo.security.filters;

import com.google.common.base.Strings;
import com.loki.caninebookmongo.security.auth.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization");
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String token = authorizationHeader.replace("Bearer","");

            //If the token was signed with the key
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Token.SECURITY_KEY.getBytes()))
                    .build().parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    null
            );
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        }catch (JwtException e){
          System.out.println(e.getMessage());
        }


        filterChain.doFilter(request,response);

    }
}
