package com.example.board.service.login;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Member;
import com.example.board.dto.login.LoginDTO;
import com.example.board.exception.NotExistMemberException;
import com.example.board.exception.PasswordIsNotMatchException;
import com.example.board.security.JwtProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.security.auth.login.FailedLoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class LoginService {

    private final MemberRepository memberRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public LoginService(MemberRepository memberRepository,EntityManager entityManager
            ,PasswordEncoder passwordEncoder,JwtProvider jwtProvider){
        this.memberRepository =memberRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
    }

    @Transactional
    public String login(LoginDTO loginDTO){
        log.info("loginService logic operate");
        Optional<Member> result = memberRepository.findById(loginDTO.getEmail());
        if(result.isEmpty())
            throw new NotExistMemberException("가입되지 않은 회원입니다.");

        Member member = result.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            throw new PasswordIsNotMatchException("잘못된 비밀번호입니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRoleName()));
        return jwtProvider.createToken(member.getEmail(), authorities);
    }
}
