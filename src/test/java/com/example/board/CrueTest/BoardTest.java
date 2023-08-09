package com.example.board.CrueTest;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.board.SaveBoardDTO;
import com.example.board.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardTest {
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;
    private MemberService memberService;

    @Autowired
    public BoardTest(BoardRepository boardRepository,MemberRepository memberRepository,MemberService memberService){
        this.boardRepository =boardRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Test
    public void addBoard(){
        Member member = memberRepository.findById("psy2173@").get();
        Board board = Board.builder().title("1").content("test").member(member).build();
        boardRepository.save(board);
        log.info("sucess addBoard");
    }

}
