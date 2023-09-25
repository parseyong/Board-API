package com.example.board.dto.CoolSMS;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationMessageDto {
    private String phoneNum;
    private String authenticationNumber;
}
