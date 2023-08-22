package com.example.board.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteBoardDTO {
    private int boardNum;
    private String email;
}
