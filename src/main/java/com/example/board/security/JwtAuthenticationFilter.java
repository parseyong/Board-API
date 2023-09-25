package com.example.board.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*
        GenericFilterBean을 자바 빈으로 등록하면 해당 필터가 두번 실행되는 문제가 발생한다.
        이를 해결하기 위해서는 GenericFilterBean이 아닌 OncePerRequestFilter을 상속받으면 된다.
    */

    private final JwtProvider jwtProvider;
    @Autowired
    public JwtAuthenticationFilter(JwtProvider jwtProvider){
       this.jwtProvider=jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("filter JWT");
        String token = jwtProvider.resolveToken(request);

        log.info(token);
        if (token != null && jwtProvider.validateToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);
            // contextHolder에 인증객체저장. 인가과정을 거친 후 해당 인증객체는 삭제된다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("토큰 인증완료");
        }

        filterChain.doFilter(request, response);
    }
}
