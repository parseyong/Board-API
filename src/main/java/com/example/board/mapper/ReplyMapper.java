package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Reply;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.reply.ReplyInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ReplyMapper {
    ReplyMapper INSTANCE = Mappers.getMapper(ReplyMapper.class);
    Reply replyInfoDTOToReply(ReplyInfoDTO replyInfoDTO);
    ReplyInfoDTO replyToReplyInfoDTO(Reply reply);
}
