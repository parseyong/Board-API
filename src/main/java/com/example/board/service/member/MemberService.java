package com.example.board.service.member;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.member.MemberInfoDTO;
import com.example.board.dto.member.MemberDeleteDTO;
import com.example.board.dto.member.MemberSaveDTO;
import com.example.board.dto.member.MemberUpdateDTO;
import com.example.board.exception.*;
import com.example.board.security.JwtProvider;
import com.example.board.service.CoolSMS.PhoneAuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final PhoneAuthenticationService phoneAuthenticationService;

    @Autowired
    public MemberService(MemberRepository memberRepository,PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                         PhoneAuthenticationService phoneAuthenticationService){
        this.memberRepository =memberRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
        this.phoneAuthenticationService=phoneAuthenticationService;
    }

    public void saveMember(MemberSaveDTO memberSaveDTO){

        String phoneNum = memberSaveDTO.getPhoneNum().toString();
        if(!phoneAuthenticationService.isAuthenticatedNum(phoneNum)){
            throw new NotAuthenticatedNumberException("전화번호 인증이 되지 않았습니다.");
        }

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
    public void updateMember(MemberUpdateDTO memberUpdateDTO){

        Optional<Member> result = memberRepository.findById(memberUpdateDTO.getEmail());
        if(result.isEmpty())
            throw new NotExistMemberException("회원이 존재하지 않습니다.");
        Member member= result.get();
        member.changeName(memberUpdateDTO.getName());
        member.changePassword(passwordEncoder.encode(memberUpdateDTO.getPassword()));

        memberRepository.save(member);
    }
    //jwt토큰에서 회원정보를 가져오는 메소드
    @Transactional
    public MemberInfoDTO readMember(String email,ServletRequest request){
        if(!email.equals(readMemberByToken(request))){
            throw new InvalidReadMemberException("회원정보를 읽기위한 권한이 없습니다.");
        }

        Optional<Member> result = memberRepository.findById(email);
        if(result.isEmpty()){
            throw new NotExistMemberException("회원이 존재하지 않습니다");
        }
        Member member =result.get();
        List<Board> boardList = member.getBoard();
        List<String> titleList= new ArrayList<>();
        for(int i=0;i<boardList.size();i++){
            titleList.add(boardList.get(i).getTitle());
        }
        MemberInfoDTO memberDTO = MemberInfoDTO.builder()
                            .email(email)
                            .boardList(titleList)
                            .name(member.getName())
                            .build();
        log.info(memberDTO);
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
