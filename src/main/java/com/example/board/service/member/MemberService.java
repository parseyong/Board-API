package com.example.board.service.member;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.member.MemberDTO;
import com.example.board.dto.member.MemberDeleteDTO;
import com.example.board.dto.member.MemberSaveDTO;
import com.example.board.exception.DuplicatedEmailException;
import com.example.board.exception.NotExistMemberException;
import com.example.board.exception.PasswordIsNotMatchException;
import com.example.board.security.JwtProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
    private final JwtProvider jwtProvider;

    @Autowired
    public MemberService(MemberRepository memberRepository, EntityManager entityManager,PasswordEncoder passwordEncoder,
                         JwtProvider jwtProvider){
        this.memberRepository =memberRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
    }

    public void saveMember(MemberSaveDTO memberSaveDTO){

        Optional<Member> result = memberRepository.findById(memberSaveDTO.getEmail());
        if(result.isPresent())
            throw new DuplicatedEmailException("해당 이메일로 이미 가입된 회원이 있습니다.");

        Member member = Member.builder().email(memberSaveDTO.getEmail())
                        .name(memberSaveDTO.getName())
                        .password(passwordEncoder.encode(memberSaveDTO.getPassword()))
                        .roleName(memberSaveDTO.getRoleName())
                        .build();

        memberRepository.save(member);
    }
    public void updateMember(MemberSaveDTO memberSaveDTO){

        Member member = Member.builder().email(memberSaveDTO.getEmail())
                .name(memberSaveDTO.getName())
                .password(passwordEncoder.encode(memberSaveDTO.getPassword()))
                .roleName(memberSaveDTO.getRoleName())
                .build();

        memberRepository.save(member);
    }
    //jwt토큰에서 회원정보를 가져오는 메소드
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
    @Transactional
    public String readMemberByToken(ServletRequest request){
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        String email = jwtProvider.getUsername(token);
        return email;
    }
    @Transactional
    public void deleteMember(MemberDeleteDTO memberDeleteDTO){

        Optional<Member> result = memberRepository.findById(memberDeleteDTO.getEmail());
        if(result.isEmpty())
            throw new NotExistMemberException("회원이 존재하지 않습니다.");
        Member member=result.get();
        if(passwordEncoder.matches(memberDeleteDTO.getPassword(),member.getPassword()))
            memberRepository.deleteById(member.getEmail());
        else
            throw new PasswordIsNotMatchException("비밀번호가 일치하지 않습니다.");

    }
}
