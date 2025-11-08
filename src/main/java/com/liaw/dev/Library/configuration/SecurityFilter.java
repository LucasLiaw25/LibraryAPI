package com.liaw.dev.Library.configuration;

import com.liaw.dev.Library.dto.JWTData;
import com.liaw.dev.Library.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String head = request.getHeader("Authorization");

        if (Strings.isNotEmpty(head) && head.startsWith("Bearer ")){
            String token = head.substring("Bearer ".length()).trim();
            Optional<JWTData> jwtData = service.verifyToken(token);
            if (jwtData.isPresent()){
                UsernamePasswordAuthenticationToken userPass = new UsernamePasswordAuthenticationToken(
                        jwtData, null, Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(userPass);
            }
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
