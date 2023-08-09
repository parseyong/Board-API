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
    회원정보 변경과 같은 비밀번호 정보가 필요한 작업에 사용하는 dto는 따로 분리
 */

public class MemberDTO {

    private String email;

    private List<Board> board;
}
