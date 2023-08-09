package com.example.board.dto.board;

import com.example.board.dto.file.FileInfoDTO;
import com.example.board.dto.reply.ReplyInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 게시글의 정보를 전달할 때 사용되는 dto, 로그인된 회원과 해당 게시글의 회원이 일치하면 수정,삭제버튼을 활성화 시키기위해 isMyBoard필드 추가
public class BoardInfoDTO {

    private int boardNum;

    private String title;

    private String content;

    private String name;
    
    private boolean isMyBoard;
    
    private List<ReplyInfoDTO> replyDTO;

    private List<FileInfoDTO> fileDTO;
}
