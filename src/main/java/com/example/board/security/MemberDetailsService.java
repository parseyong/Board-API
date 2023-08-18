package com.example.board.security;

import com.example.board.Repository.MemberRepository;
import com.example.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

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
