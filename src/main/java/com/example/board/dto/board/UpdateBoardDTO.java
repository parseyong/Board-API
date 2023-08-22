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
    @NotEmpty
    @NotBlank
    private String title;
    @NotEmpty
    @NotBlank
    private String content;

    private int boardNum;
}
