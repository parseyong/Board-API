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

    int boardNum;

    String email;
}
