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
/*
    회원 정보를 전달하는 DTO, 비밀번호는 전달할 필요가 없기에 password필드는 제외하였다.
 */

public class MemberDTO {

    private String email;

    private List<Board> board;
}
