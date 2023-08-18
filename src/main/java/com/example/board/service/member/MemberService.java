package com.example.board.service.member;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.member.MemberDTO;
import com.example.board.dto.member.MemberRegisterDTO;
import com.example.board.exception.DuplicatedEmailException;
import com.example.board.exception.NotExistMemberException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, EntityManager entityManager,PasswordEncoder passwordEncoder){
        this.memberRepository =memberRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.passwordEncoder=passwordEncoder;
    }

    public void registeMember(MemberRegisterDTO memberRegisterDTO){

        Optional<Member> result = memberRepository.findById(memberRegisterDTO.getEmail());
        if(result.isPresent())
            throw new DuplicatedEmailException("해당 이메일로 이미 가입된 회원이 있습니다.");

        Member member = Member.builder().email(memberRegisterDTO.getEmail())
                        .name(memberRegisterDTO.getName())
                        .password(passwordEncoder.encode(memberRegisterDTO.getPassword()))
                        .roleName(memberRegisterDTO.getRoleName())
                        .build();

        memberRepository.save(member);
    }
    //email정보를 통해 member정보를 읽어 memberDTO를 반환
    @Transactional
    public MemberDTO readMember(String email){
        Optional<Member> result = memberRepository.findById(email);
        if(!result.isPresent()){
            throw new NotExistMemberException("회원이 존재하지 않습니다");
        }
        Member member =result.get();
        member.getBoard().size();
        MemberDTO memberDTO = MemberDTO.builder()
                            .email(email)
                            .boardList(member.getBoard())
                            .name(member.getName())
                            .build();
        return memberDTO;
    }
}
