package com.postly.api.config

import com.postly.api.repository.MemberRepository
import com.postly.api.service.SecurityService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    SecurityService securityService

    @Autowired
    MemberRepository memberRepository

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request)

        if (token != null) {
            String username = securityService.validateToken(token)
            UserDetails user = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException("Invalid token"))

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            SecurityContextHolder.getContext().setAuthentication(auth)
        }
        filterChain.doFilter(request, response)
    }

    private static String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization")

        if (authHeader == null) {
            return null
        }
        return authHeader.replace("Bearer ", "")
    }
}
