package com.example.board.mapper;

import com.example.board.domain.Reply;
import com.example.board.domain.Reply.ReplyBuilder;
import com.example.board.dto.reply.ReplyInfoDTO;
import com.example.board.dto.reply.ReplyInfoDTO.ReplyInfoDTOBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T22:52:26+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
public class ReplyMapperImpl implements ReplyMapper {

    @Override
    public Reply replyInfoDTOToReply(ReplyInfoDTO replyInfoDTO) {
        if ( replyInfoDTO == null ) {
            return null;
        }

        ReplyBuilder reply = Reply.builder();

        reply.content( replyInfoDTO.getContent() );
        reply.replyler( replyInfoDTO.getReplyler() );

        return reply.build();
    }

    @Override
    public ReplyInfoDTO replyToReplyInfoDTO(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyInfoDTOBuilder replyInfoDTO = ReplyInfoDTO.builder();

        replyInfoDTO.content( reply.getContent() );
        replyInfoDTO.replyler( reply.getReplyler() );

        return replyInfoDTO.build();
    }
}
