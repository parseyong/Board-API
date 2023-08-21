package com.example.board.service.board;

import com.example.board.Repository.BoardRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.QBoard;
import com.example.board.domain.QMember;
import com.example.board.dto.board.*;
import com.example.board.dto.member.MemberDTO;
import com.example.board.exception.NotExistBoardException;
import com.example.board.mapper.BoardMapper;
import com.example.board.security.JwtProvider;
import com.example.board.service.image.ImageService;
import com.example.board.service.member.MemberService;
import com.example.board.service.reply.ReplyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
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
    private final PagingService pagingService;
    private final JwtProvider jwtProvider;

    @Autowired
    public BoardService(BoardRepository boardRepository, EntityManager entityManager,JwtProvider jwtProvider,
                        MemberService memberService, ImageService imageService, ReplyService replyService,PagingService pagingService){
        this.boardRepository =boardRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.memberService=memberService;
        this.imageService=imageService;
        this.replyService=replyService;
        this.jwtProvider=jwtProvider;
        this.pagingService=pagingService;
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
    public BoardInfoDTO readBoard(ReadBoardDTO readBoardDTO,ServletRequest request){
        Optional<Board> result = boardRepository.findById(readBoardDTO.getBoardNum());

        if(result.isEmpty()){
            throw new NotExistBoardException("존재하지 않는 게시글입니다");
        }
        Board board = result.get();
        board.getMember();
        return boardToBoardInfoDTO(board,readBoardDTO,request);
    }
    public BoardInfoDTO boardToBoardInfoDTO(Board board,ReadBoardDTO readBoardDTO,ServletRequest request){
        readBoardDTO.setEmail(memberService.readMemberByToken(request));
        BoardInfoDTO boardInfoDTO = BoardMapper.INSTANCE.boardToBoardInfoDTO(board);

        boardInfoDTO.setMyBoard(board.getMember().getEmail().equals(readBoardDTO.getEmail()));
        boardInfoDTO.setReplyInfoDTOList(replyService.readReply(board,readBoardDTO));
        boardInfoDTO.setFilePathList(imageService.readImage(board));
        log.info(boardInfoDTO);
        return boardInfoDTO;
    }
    @Transactional
    public PagingRequestDTO readPreviewBoard(int pageNum){
        PagingRequestDTO pagingRequestDTO = pagingService.getPagingRequestDTO(pageNum);
        QBoard qBoard=QBoard.board;
        Pageable pageable= PageRequest.of(pageNum-1,10);

        List<Board> result = jpaQueryFactory.select(qBoard)
                .from(qBoard)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<PreviewBoardDTO> previewBoardDTOList=new ArrayList<>();
        for(int i=0;i< result.size();i++){
            Board board = result.get(i);
            PreviewBoardDTO previewBoardDTO = BoardMapper.INSTANCE.boardToPreviewBoardDTO(board);
            previewBoardDTO.setName(board.getMember().getName());
            previewBoardDTO.setReplyCnt(board.getReply().size());
            previewBoardDTOList.add(previewBoardDTO);
        }
        pagingRequestDTO.setPreviewBoardDTOList(previewBoardDTOList);
        return pagingRequestDTO;
    }
}
