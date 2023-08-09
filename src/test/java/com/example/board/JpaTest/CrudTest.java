package com.example.board.JpaTest;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.MemberRepository;
import com.example.board.Repository.ReplyRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.Reply;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest

public class CrudTest {

    private BoardRepository boardRepository;
    private MemberRepository memberRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public CrudTest(BoardRepository boardRepository,MemberRepository memberRepository,ReplyRepository replyRepository){
        this.boardRepository =boardRepository;
        this.memberRepository = memberRepository;
        this.replyRepository=replyRepository;
    }

    @Test
    public void registeMember(){
        Member member = Member.builder().email("psy2173@").name("박세용").password("1234").build();
        memberRepository.save(member);
    }

}
