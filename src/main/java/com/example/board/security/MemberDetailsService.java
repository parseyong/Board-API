package com.example.board.security;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        log.info("loadUserByUsername method is start");
        Optional<Member> result = memberRepository.findById(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = result.get();
        authorities.add(new SimpleGrantedAuthority(member.getRoleName()));

        return MemberDetails.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .build();

    }
}
