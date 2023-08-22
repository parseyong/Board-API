package com.example.board.service.reply;

import com.example.board.Repository.BoardRepository;
import com.example.board.Repository.ReplyRepository;
import com.example.board.domain.Board;
import com.example.board.domain.Reply;
import com.example.board.dto.board.ReadBoardDTO;
import com.example.board.dto.board.UpdateBoardDTO;
import com.example.board.dto.reply.ReplyInfoDTO;
import com.example.board.dto.reply.SaveReplyDTO;
import com.example.board.dto.reply.UpdateReplyDTO;
import com.example.board.exception.NotExistBoardException;
import com.example.board.exception.NotExistReplyException;
import com.example.board.mapper.ReplyMapper;
import com.example.board.service.member.MemberService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReplyService {

    private ReplyRepository replyRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberService memberService;
    private final BoardRepository boardRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,EntityManager entityManager,MemberService memberService,
                        BoardRepository boardRepository){
        this.replyRepository=replyRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
        this.memberService=memberService;
        this.boardRepository=boardRepository;
    }

    public List<ReplyInfoDTO> readReply(Board board, ReadBoardDTO readBoardDTO){

        List<ReplyInfoDTO> replyInfoDTOList=new ArrayList<>();
        List<Reply> replyList = board.getReply();
        for(int i=0;i< replyList.size();i++){
            Reply reply = replyList.get(i);
            ReplyInfoDTO replyInfoDTO= ReplyMapper.INSTANCE.replyToReplyInfoDTO(reply);
            if(board.getMember().getEmail().equals(readBoardDTO.getEmail()))
                replyInfoDTO.setMyReply(true);
            else
                replyInfoDTO.setMyReply(false);

            replyInfoDTO.setReplyler(reply.getReplyler());
            replyInfoDTOList.add(replyInfoDTO);
        }
        return  replyInfoDTOList;
    }
    public void saveReply(SaveReplyDTO saveReplyDTO, ServletRequest request){
        String email = memberService.readMemberByToken(request);
        Optional<Board> board = boardRepository.findById(saveReplyDTO.getBoardNum());
        if(board.isEmpty())
            throw new NotExistBoardException("존재하지 않는 게시글입니다.");

        Reply reply = Reply.builder()
                .replyler(email)
                .content(saveReplyDTO.getContent())
                .board(board.get())
                .build();

        replyRepository.save(reply);
    }
    public void updateReply(UpdateReplyDTO updateReplyDTO){
        Optional<Reply> result = replyRepository.findById(updateReplyDTO.getReplyNum());
        if(result.isEmpty())
            throw new NotExistReplyException("존재하지 않는 댓글입니다.");
        Reply originReply = result.get();
        Reply reply = Reply.builder()
                .board(originReply.getBoard())
                .content(updateReplyDTO.getContent())
                .replyNum(originReply.getReplyNum())
                .replyler(originReply.getReplyler())
                .build();
        replyRepository.save(reply);
    }
    public void deleteReply(int replyNum){
        if(!replyRepository.existsById(replyNum))
            throw new NotExistReplyException("존재하지 않는 댓글입니다.");
        replyRepository.deleteById(replyNum);
    }
}
