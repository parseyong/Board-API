package com.example.board.dto.board;

import com.example.board.domain.Member;
import com.example.board.domain.Reply;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 게시글 등록,수정할 떄 사용되는 dto
public class SaveBoardDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
