package com.example.board.controller.member;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Member;
import com.example.board.dto.member.MemberDTO;
import com.example.board.dto.member.MemberRegisterDTO;
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

    @Autowired
    public MemberController(MemberRepository memberRepository,MemberService memberService){
        this.memberRepository=memberRepository;
        this.memberService=memberService;
    }

    @PostMapping ("/members")
    public ResponseEntity<Object> registMember(@RequestBody @Valid MemberRegisterDTO memberRegisterDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            // 에러 정보를 Map에 담아서 응답으로 반환
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        String email = memberRegisterDTO.getEmail();
        memberService.registeMember(memberRegisterDTO);
        return ResponseEntity.ok().body(memberRegisterDTO);
    }

    @GetMapping("/members/{email}")
    public ResponseEntity<Object> readMember(@PathVariable String email){
        MemberDTO memberDTO = memberService.readMember(email);
        return ResponseEntity.ok().body(memberDTO);
    }
}
