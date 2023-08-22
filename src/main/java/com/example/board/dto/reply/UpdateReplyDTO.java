package com.example.board.dto.reply;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateReplyDTO {
    @NotBlank
    @NotEmpty
    private String content;

    private int replyNum;
}
