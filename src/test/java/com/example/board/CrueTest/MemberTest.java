package com.example.board.CrueTest;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.MemberRepository;
import com.example.board.dto.member.MemberDTO;
import com.example.board.domain.Member;
import com.example.board.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class MemberTest {
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;
    private MemberService memberService;

    @Autowired
    public MemberTest(BoardRepository boardRepository,MemberRepository memberRepository,MemberService memberService){
        this.boardRepository =boardRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Test
    public void registeMember(){
        Member member = com.example.board.domain.Member.builder().email("psy2173@").name("박세용").password("1234").build();
        memberRepository.save(member);
    }

    @Test
    public void readMember(){
        MemberDTO member =memberService.readMember("psy2173@");
        System.out.println(member);
    }

}
