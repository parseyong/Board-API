package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.board.BoardInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);
    Board boardInfoDTOToBoard(BoardInfoDTO boardInfoDTO);

    BoardInfoDTO boardToBoardInfoDTO(Board board);
}
