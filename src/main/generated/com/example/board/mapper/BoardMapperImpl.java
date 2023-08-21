package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Board.BoardBuilder;
import com.example.board.domain.Member;
import com.example.board.dto.board.BoardInfoDTO;
import com.example.board.dto.board.BoardInfoDTO.BoardInfoDTOBuilder;
import com.example.board.dto.board.PreviewBoardDTO;
import com.example.board.dto.board.PreviewBoardDTO.PreviewBoardDTOBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-22T00:34:29+0900",
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
    public BoardInfoDTO boardToBoardInfoDTO(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardInfoDTOBuilder boardInfoDTO = BoardInfoDTO.builder();

        boardInfoDTO.name( boardMemberName( board ) );
        boardInfoDTO.email( boardMemberEmail( board ) );
        boardInfoDTO.boardNum( board.getBoardNum() );
        boardInfoDTO.title( board.getTitle() );
        boardInfoDTO.content( board.getContent() );

        return boardInfoDTO.build();
    }

    @Override
    public PreviewBoardDTO boardToPreviewBoardDTO(Board board) {
        if ( board == null ) {
            return null;
        }

        PreviewBoardDTOBuilder previewBoardDTO = PreviewBoardDTO.builder();

        previewBoardDTO.boardNum( board.getBoardNum() );
        previewBoardDTO.title( board.getTitle() );

        return previewBoardDTO.build();
    }

    private String boardMemberName(Board board) {
        if ( board == null ) {
            return null;
        }
        Member member = board.getMember();
        if ( member == null ) {
            return null;
        }
        String name = member.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String boardMemberEmail(Board board) {
        if ( board == null ) {
            return null;
        }
        Member member = board.getMember();
        if ( member == null ) {
            return null;
        }
        String email = member.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
