package com.example.board.dto.board;

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
public class UpdateBoardDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private int boardNum;
}
