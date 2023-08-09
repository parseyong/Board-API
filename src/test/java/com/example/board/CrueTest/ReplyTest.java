package com.example.board.CrueTest;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.MemberRepository;
import com.example.board.Repository.ReplyRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Reply;
import com.example.board.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Log4j2
public class ReplyTest {

    private BoardRepository boardRepository;
    private ReplyRepository replyRepository;

    @Autowired
    public ReplyTest(BoardRepository boardRepository,ReplyRepository replyRepository){
        this.boardRepository =boardRepository;
        this.replyRepository=replyRepository;
    }

    @Test
    public void addReply(){
        Board board = boardRepository.findById(1).get();

        Reply reply = Reply.builder().content("댓글테스트2").replyler("박세용").board(board).build();
        log.info(reply);
        replyRepository.save(reply);
    }
}
