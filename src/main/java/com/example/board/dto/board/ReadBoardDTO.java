package com.example.board.dto.board;


import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReadBoardDTO {
    @NotNull
    int boardNum;

    String email;
}
