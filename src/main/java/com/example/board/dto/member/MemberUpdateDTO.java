package com.example.board.dto.member;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberUpdateDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String email;
}
