package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Board.BoardBuilder;
import com.example.board.domain.Member;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.BoardInfoDTO.BoardInfoDTOBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-18T22:52:26+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardInfoDTOToBoard(BoardInfoDTO boardInfoDTO) {
        if ( boardInfoDTO == null ) {
            return null;
        }

        BoardBuilder board = Board.builder();

        board.boardNum( boardInfoDTO.getBoardNum() );
        board.title( boardInfoDTO.getTitle() );
        board.content( boardInfoDTO.getContent() );

        return board.build();
    }

    @Override
    public BoardInfoDTO boardToBoardInfoDTO(Board board, Member member) {
        if ( board == null && member == null ) {
            return null;
        }

        BoardInfoDTOBuilder boardInfoDTO = BoardInfoDTO.builder();

        if ( board != null ) {
            boardInfoDTO.boardNum( board.getBoardNum() );
            boardInfoDTO.title( board.getTitle() );
            boardInfoDTO.content( board.getContent() );
        }
        if ( member != null ) {
            boardInfoDTO.name( member.getName() );
            boardInfoDTO.email( member.getEmail() );
        }

        return boardInfoDTO.build();
    }
}
