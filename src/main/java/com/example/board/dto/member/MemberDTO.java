package com.example.board.dto.member;

import com.example.board.domain.Board;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private String email;
    private String name;
    private List<Board> boardList;
}
