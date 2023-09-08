package com.example.board.dto.reply;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveReplyDTO {
    @NotBlank
    private String content;

    private int boardNum;
}
