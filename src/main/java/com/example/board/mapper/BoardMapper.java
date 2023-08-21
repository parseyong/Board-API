package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.PreviewBoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);
    Board boardInfoDTOToBoard(BoardInfoDTO boardInfoDTO);
    @Mapping(source = "member.name",target = "name")
    @Mapping(source = "member.email",target = "email")
    BoardInfoDTO boardToBoardInfoDTO(Board board);

    PreviewBoardDTO boardToPreviewBoardDTO(Board board);
}
