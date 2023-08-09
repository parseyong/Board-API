package com.example.board.dto.reply;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveReplyDTO {

    private String content;

    private String replyer;

    private int boardNum;
}
