package com.example.board.service.reply;

import com.example.board.Repository.ReplyRepository;
import com.example.board.domain.Board;
import com.example.board.domain.QReply;
import com.example.board.domain.Reply;
import com.example.board.dto.reply.ReplyInfoDTO;
import com.example.board.mapper.ReplyMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ReplyService {

    private ReplyRepository replyRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ReplyService(ReplyRepository replyRepository,EntityManager entityManager){
        this.replyRepository=replyRepository;
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(this.entityManager);
    }

    public List<ReplyInfoDTO> readReply(Board board){

        List<ReplyInfoDTO> replyInfoDTOList=new ArrayList<>();
        List<Reply> replyList = board.getReply();

        for(int i=0;i< replyList.size();i++){
            Reply reply = replyList.get(i);
            ReplyInfoDTO replyInfoDTO= ReplyMapper.INSTANCE.replyToReplyInfoDTO(reply);
            replyInfoDTO.setMyReply(true); // jwt를 통해 확인. 추후에 구현예정
            replyInfoDTO.setReplyler(reply.getReplyler());
            replyInfoDTOList.add(replyInfoDTO);
        }
        return  replyInfoDTOList;
    }
}
