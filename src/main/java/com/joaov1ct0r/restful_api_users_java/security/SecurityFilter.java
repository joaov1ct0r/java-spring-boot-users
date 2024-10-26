package com.joaov1ct0r.restful_api_users_java.security;

import com.joaov1ct0r.restful_api_users_java.modules.auth.services.JWTService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.UnauthorizedException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.getToken(request);

        if (token.isPresent()) {
            var decodedToken = this.jwtService.decodeToken(token.get());
            var user = this.userRepository.findById(UUID.fromString(decodedToken.getSubject()));

            if (user.isEmpty()) {
                //throw new UnauthorizedException("Não autorizado");
                throw new BadRequestException("Não autorizado");
            }

            UserDetails details = user.get();

            var auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.setAttribute("userId", user.get().getId().toString());
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        Optional<String> token = Optional.empty();
        var cookies = request.getCookies();

        if (cookies == null) {
            return token;
        }


        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("authorization")) {
                token = Optional.of(cookie.getValue());
            }
        }

        if (token.isEmpty()) {
            throw new UnauthorizedException("Não autorizado");
        }

        return token;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        if (path.equals("/signin/")) return true;

        if (path.equals("/signup/")) return true;

        return false;
    }
}
