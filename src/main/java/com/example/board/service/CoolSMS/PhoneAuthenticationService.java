package com.example.board.service.CoolSMS;

import com.example.board.dto.CoolSMS.AuthenticationMessageDto;
import com.example.board.dto.CoolSMS.PhoneNumDto;
import com.example.board.exception.AuthenticationNumIsNotMatchException;
import lombok.extern.log4j.Log4j2;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

@Service
@Log4j2
public class PhoneAuthenticationService {
    @Value("${CoolSMS.Api.Key}")
    private String api_key; // 발급받은 api_key
    @Value("${CoolSMS.Api.Secret}")
    private String api_Secret; // 발급받은 api_secret
    @Value("${CoolSMS.Caller}")
    private String callId; // 발신자 번호
    private HashMap<String,String> authenticationNumberMap = new HashMap<>();//key-전화번호 value-인증번호
    private HashSet<String> authenticationedPhoneNumSet = new HashSet<>();//인증확인이 되면 해당 셋에 저장.
    public void sendMessage(PhoneNumDto phoneNumDto){
        Random randomNum = new Random();
        String phoneNum = phoneNumDto.getPhoneNum();
        int authenticationNumber = randomNum.nextInt(0,9999); // 인증번호 생성

        Message coolSms = new  Message(api_key,api_Secret);
        
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNum);    // 수신전화번호
        params.put("from", callId);    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "sms");     // 타입
        params.put("text", "FoodKing 휴대폰인증 메시지 : 인증번호는" + "["+authenticationNumber+"]" + "입니다.");
        log.info("메시지 전송완료==========================================================================");
        try {
            JSONObject obj = coolSms.send(params);
            authenticationNumberMap.put(phoneNum, String.valueOf(authenticationNumber)); //인증번호 확인을 위한 저장.
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
    public void authenticationMessage(AuthenticationMessageDto authenticationMessageDto) {
        String authenticationNum = authenticationNumberMap.get(authenticationMessageDto.getPhoneNum());
        if(!authenticationNum.equals(authenticationMessageDto.getAuthenticationNumber()))
            throw new AuthenticationNumIsNotMatchException("인증번호가 올바르지 않습니다");
        authenticationedPhoneNumSet.add(authenticationMessageDto.getPhoneNum());
        log.info("인증완료==========================================================================");
    }
    public boolean isAuthenticatedNum(String phoneNum){

        if(authenticationedPhoneNumSet.contains(phoneNum)){
            authenticationedPhoneNumSet.remove(phoneNum);
            authenticationNumberMap.remove(phoneNum);
            return true;
        }

        return false;
    }

}
