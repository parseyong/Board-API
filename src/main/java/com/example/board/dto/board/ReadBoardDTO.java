package com.example.board.dto.board;

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
