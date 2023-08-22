package com.example.board.controller.member;

import com.example.board.Repository.MemberRepository;
import com.example.board.dto.login.LoginDTO;
import com.example.board.dto.member.MemberInfoDTO;
import com.example.board.dto.member.MemberDeleteDTO;
import com.example.board.dto.member.MemberSaveDTO;
import com.example.board.service.login.LoginService;
import com.example.board.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LoginService loginService;

    @Autowired
    public MemberController(MemberRepository memberRepository,MemberService memberService,
                            LoginService loginService){
        this.memberRepository=memberRepository;
        this.memberService=memberService;
        this.loginService=loginService;
    }

    @PostMapping ("/members")
    public ResponseEntity<Object> registMember(@RequestBody @Valid MemberSaveDTO memberSaveDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        String email = memberSaveDTO.getEmail();
        memberService.saveMember(memberSaveDTO);
        return ResponseEntity.created(null).body(memberSaveDTO);
    }
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO loginDTO,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        String token = loginService.login(loginDTO);
        return ResponseEntity.ok().body(token);
    }
    @GetMapping("/members/{email}")
    public ResponseEntity<Object> readMember(@PathVariable String email){

        MemberInfoDTO memberDTO = memberService.readMember(email);
        return ResponseEntity.ok().body(memberDTO);
    }
    @DeleteMapping("/members/{email}")
    public ResponseEntity<Object> deleteMember(@PathVariable String email,@RequestBody @Valid MemberDeleteDTO memberDeleteDTO,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        memberDeleteDTO.setEmail(email);
        memberService.deleteMember(memberDeleteDTO);
        return ResponseEntity.ok().body("회원 탈퇴 완료");
    }
    @PatchMapping("/members/{email}")
    public ResponseEntity<Object> updateMember(@PathVariable String email,@RequestBody @Valid MemberSaveDTO memberSaveDTO,
                                               BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        memberSaveDTO.setEmail(email);
        memberService.updateMember(memberSaveDTO);
        return ResponseEntity.ok().body(memberSaveDTO);
    }

}
