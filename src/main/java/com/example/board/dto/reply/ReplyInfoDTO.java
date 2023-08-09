package com.example.board.dto.reply;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyInfoDTO {

    private String content;

    private String replyer;

    private boolean isMyReply;

}
