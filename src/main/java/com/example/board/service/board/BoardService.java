package com.example.board.service.board;

import com.example.board.Repository.BoardRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.Reply;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.PreviewBoardDTO;
import com.example.board.dto.board.ReadBoardDTO;
import com.example.board.dto.board.SaveBoardDTO;
import com.example.board.dto.member.MemberDTO;
import com.example.board.dto.reply.ReplyInfoDTO;
import com.example.board.exception.NotExistBoardException;
import com.example.board.mapper.BoardMapper;
import com.example.board.mapper.ReplyMapper;
import com.example.board.service.image.ImageService;
import com.example.board.service.member.MemberService;
import com.example.board.service.reply.ReplyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberService memberService;
    private final ImageService imageService;
    private final ReplyService replyService;

    @Autowired
    public BoardService(BoardRepository boardRepository, EntityManager entityManager,
                        MemberService memberService, ImageService imageService, ReplyService replyService){
        this.boardRepository =boardRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.memberService=memberService;
        this.imageService=imageService;
        this.replyService=replyService;
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
        Optional<Board> result = boardRepository.findById(readBoardDTO.getBoardNum());

        if(result.isEmpty()){
            throw new NotExistBoardException("존재하지 않는 게시글입니다");
        }
        Board board = result.get();
        Member member = board.getMember();

        return boardToBoardInfoDTO(board,member,readBoardDTO);
    }

    public BoardInfoDTO boardToBoardInfoDTO(Board board, Member member,ReadBoardDTO readBoardDTO){

        BoardInfoDTO boardInfoDTO = BoardMapper.INSTANCE.boardToBoardInfoDTO(board,member);

        boardInfoDTO.setMyBoard(member.getEmail().equals(readBoardDTO.getEmail()));
        boardInfoDTO.setReplyInfoDTOList(replyService.readReply(board));
        boardInfoDTO.setFilePathList(imageService.readImage(board));
        return boardInfoDTO;
    }
}
