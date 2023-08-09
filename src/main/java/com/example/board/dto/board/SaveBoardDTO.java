package com.example.board.dto.board;

import com.example.board.domain.Member;
import com.example.board.domain.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 게시글 등록,수정할 떄 사용되는 dto
public class SaveBoardDTO {

    @NotEmpty
    @NotBlank
    private int boardNum;
    @NotEmpty
    @NotBlank
    private String title;
    @NotEmpty
    @NotBlank
    private String content;
    @NotEmpty
    @NotBlank
    private String email;

}
