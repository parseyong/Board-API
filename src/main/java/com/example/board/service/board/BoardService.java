package com.example.board.service.board;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.QBoard;
import com.example.board.dto.board.*;
import com.example.board.dto.paging.PagingRequestDTO;
import com.example.board.exception.NotExistBoardException;
import com.example.board.exception.NotExistMemberException;
import com.example.board.mapper.BoardMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.board.domain.QBoard.board;

@Service
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final ReplyService replyService;
    private final PagingService pagingService;

    @Autowired
    public BoardService(BoardRepository boardRepository, EntityManager entityManager, MemberService memberService,
                        ImageService imageService, ReplyService replyService,PagingService pagingService,MemberRepository memberRepository){
        this.boardRepository =boardRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.memberService=memberService;
        this.imageService=imageService;
        this.replyService=replyService;
        this.pagingService=pagingService;
        this.memberRepository=memberRepository;
    }

    public int saveBoard(SaveBoardDTO saveBoardDTO,ServletRequest request){
        String email = memberService.readMemberByToken(request);
        Optional<Member> result = memberRepository.findById(email);
        if(result.isEmpty())
            throw new NotExistMemberException("존재하지 않는 회원입니다.");

        Member member = result.get();
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
        log.info(board);
        return boardToBoardInfoDTO(board,readBoardDTO,request);
    }
    public BoardInfoDTO boardToBoardInfoDTO(Board board,ReadBoardDTO readBoardDTO,ServletRequest request){
        readBoardDTO.setEmail(memberService.readMemberByToken(request));
        BoardInfoDTO boardInfoDTO = BoardMapper.INSTANCE.boardToBoardInfoDTO(board);

        boardInfoDTO.setMyBoard(board.getMember().getEmail().equals(readBoardDTO.getEmail()));
        boardInfoDTO.setReplyInfoDTOList(replyService.readReply(board,readBoardDTO));
        boardInfoDTO.setImageDTOList(imageService.readImage(board));

        return boardInfoDTO;
    }
    public List<PreviewBoardDTO> boardToPreviewBoardDTO(List<Board> boardList){
        List<PreviewBoardDTO> previewBoardDTOList=new ArrayList<>();
        for(int i=0;i< boardList.size();i++){
            Board board = boardList.get(i);
            PreviewBoardDTO previewBoardDTO = BoardMapper.INSTANCE.boardToPreviewBoardDTO(board);

            previewBoardDTO.setReplyCnt(board.getReply().size());
            previewBoardDTOList.add(previewBoardDTO);
        }
        return  previewBoardDTOList;
    }
    @Transactional
    public PagingRequestDTO readPreviewBoard(int pageNum){
        PagingRequestDTO pagingRequestDTO = pagingService.getPagingRequestDTO(pageNum);
        QBoard qBoard= board;
        Pageable pageable= PageRequest.of(pageNum-1,10);

        List<Board> result = jpaQueryFactory.select(qBoard)
                .from(qBoard)
                .offset(pageable.getOffset()) // 시작지점
                .limit(pageable.getPageSize()) //페이지의 크기
                .fetch();
        List<PreviewBoardDTO> previewBoardDTOList=boardToPreviewBoardDTO(result);

        pagingRequestDTO.setPreviewBoardDTOList(previewBoardDTOList);
        return pagingRequestDTO;
    }
    public void deleteBoard(int boardNum){
        if(!boardRepository.existsById(boardNum))
            throw new NotExistBoardException("게시글이 존재하지 않습니다.");

        boardRepository.deleteById(boardNum);
    }
    @Transactional
    public int updateBoard(UpdateBoardDTO updateBoardDTO, ServletRequest request){

        String email = memberService.readMemberByToken(request);
        Optional<Member> result = memberRepository.findById(email);
        if(result.isEmpty())
            throw new NotExistMemberException("존재하지 않는 회원입니다.");

        Optional<Board> boardResult = boardRepository.findById(updateBoardDTO.getBoardNum());
        if(boardResult.isEmpty())
            throw new NotExistBoardException("존재하지 않는 게시글입니다.");

        Board board = boardResult.get();
        board.changeTitle(updateBoardDTO.getTitle());
        board.changeContent(updateBoardDTO.getContent());

        Board savedBoard =boardRepository.save(board);
        return savedBoard.getBoardNum();
    }

}
