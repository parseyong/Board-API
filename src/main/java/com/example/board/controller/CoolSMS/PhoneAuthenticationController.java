package com.example.board.controller.CoolSMS;


import com.example.board.dto.CoolSMS.AuthenticationMessageDto;
import com.example.board.dto.CoolSMS.PhoneNumDto;
import com.example.board.service.CoolSMS.PhoneAuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class PhoneAuthenticationController {

    private final PhoneAuthenticationService phoneAuthenticationService;

    @Autowired
    public PhoneAuthenticationController(PhoneAuthenticationService phoneAuthenticationService){
        this.phoneAuthenticationService=phoneAuthenticationService;
    }

    @PostMapping("/members/sms")
    public ResponseEntity<Object> sendMessage(@RequestBody PhoneNumDto phoneNumDto){
        phoneAuthenticationService.sendMessage(phoneNumDto);
        return ResponseEntity.ok("메시지 전송완료");
    }
    @PostMapping("/members/authentication")
    public ResponseEntity<Object> authenticationMessage(@RequestBody AuthenticationMessageDto authenticationMessageDto) {
        phoneAuthenticationService.authenticationMessage(authenticationMessageDto);
        return ResponseEntity.ok("인증완료");
    }

}
