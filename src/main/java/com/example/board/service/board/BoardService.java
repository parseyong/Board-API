package com.example.board.service.board;

import com.example.board.Repository.BoardRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.ReadBoardDTO;
import com.example.board.dto.board.SaveBoardDTO;
import com.example.board.dto.member.MemberDTO;
import com.example.board.service.image.ImageService;
import com.example.board.service.member.MemberService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberService memberService;
    private final ImageService imageService;

    @Autowired
    public BoardService(BoardRepository boardRepository, EntityManager entityManager,MemberService memberService,ImageService imageService){
        this.boardRepository =boardRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.memberService=memberService;
        this.imageService=imageService;
    }

    public int addBoard(SaveBoardDTO saveBoardDTO) throws IOException {
        MemberDTO memberDTO = memberService.readMember(saveBoardDTO.getEmail());
        Member member = Member.builder().email(saveBoardDTO.getEmail()).build();

        Board board = Board.builder()
                .content(saveBoardDTO.getContent())
                .title(saveBoardDTO.getTitle())
                .member(member)
                .build();
        Board savedBoard =boardRepository.save(board);
        return savedBoard.getBoardNum();
    }
    @Transactional
    public BoardInfoDTO readBoard(ReadBoardDTO readBoardDTO){
        Board board = boardRepository.findById(readBoardDTO.getBoardNum()).get();
        Member member = board.getMember();



        BoardInfoDTO boardInfoDTO=BoardInfoDTO.builder()
                .boardNum(board.getBoardNum())
                .title(board.getTitle())
                .content(board.getContent())
                .name(member.getName())
                .isMyBoard(readBoardDTO.getEmail().equals(member.getEmail()))
                .filesPath(imageService.getFilePath(board))
                .build();
        return boardInfoDTO;
    }
}
