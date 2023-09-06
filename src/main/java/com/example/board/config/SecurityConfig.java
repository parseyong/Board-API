package com.example.board.config;

import com.example.board.security.JwtAuthenticationFilter;
import com.example.board.security.JwtProvider;
import com.example.board.exception.handler.CustomAccessDeniedHandler;
import com.example.board.exception.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/members").permitAll()
                .antMatchers("/manager").hasRole("MANAGER")
                .anyRequest().authenticated()

                .and().exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())//인증과정에서 예외발생시 핸들러 지정

                .and().exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()) //인가과정에서 예외발생시 핸들러지정

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

         /*
        	rest api에서 CSRF를 disable하는 이유
			rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않는다.
            rest api에서 client는 권한이 필요한 요청을 하기 위해서는 요청에 필요한 인증 정보를(OAuth2, jwt토큰 등)을 포함시켜야 한다.
            악의적인 csrf공격 코드에는 이러한 토큰이 포함되어있지 않기때문에 악의적인 공격이 인증실패로인해 실행되지 않는다.
            따라서 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
        */
        http.csrf().disable();
        return http.build();
    }
}