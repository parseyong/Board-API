package com.example.board.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PreviewBoardDTO {

    private int boardNum;
    private String title;
    private String name;
    private int replyCnt;
}
