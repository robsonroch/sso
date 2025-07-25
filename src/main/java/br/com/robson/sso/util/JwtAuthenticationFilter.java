package br.com.robson.sso.util;

import br.com.robson.sso.repositories.UserFakeRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.JwtException;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserFakeRepository userRepo;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserFakeRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/sso/auth/login")
                || path.equals("/sso/openapi.yaml")
                || path.startsWith("/sso/swagger-ui")
                || path.startsWith("/sso/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                String userId = jwtUtil.extractUserId(token);

                autenticarUsuario(username, userId);
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String username, String userId) {
        var user = userRepo.buscarPorId(userId).orElse(null);
        if (user == null) return;

        var authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        authentication.setDetails(userId); // guarda o ID real para uso no Autorizador

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}