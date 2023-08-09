package com.example.board.dto.login;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 로그인을 할떄 이메일과 비밀번호를 입력받아 회원정보를 확인하는데 사용되는 dto
public class LoginDTO {

    @NotEmpty
    @NotBlank
    private String email;
    @NotEmpty
    @NotBlank
    private String password;

}
