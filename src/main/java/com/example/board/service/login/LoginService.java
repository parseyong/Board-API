package com.example.board.service.login;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Member;
import com.example.board.dto.login.LoginDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.security.auth.login.FailedLoginException;
import java.util.Optional;

@Service
@Log4j2
public class LoginService {

    private final MemberRepository memberRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public LoginService(MemberRepository memberRepository,EntityManager entityManager){
        this.memberRepository =memberRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
    }

    public boolean isLoginSuccess(LoginDTO loginDTO) throws FailedLoginException {

        Optional<Member> result = memberRepository.findById(loginDTO.getEmail());
        if(result.isPresent()){
            Member member=result.get();
            if(member.getPassword().equals(loginDTO.getPassword())){
                return true;
            }
        }
        throw new FailedLoginException();
    }
}
