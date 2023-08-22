package com.example.board.dto.member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberInfoDTO {
    private String email;
    private String name;
    private List<String> boardList;
}
