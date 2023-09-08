package com.example.board.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Log4j2
@Component
public class JwtProvider {

    // 되도록 시크릿키는 유추하기 어렵게 복잡하게 설정하는 것이 좋다.
    private String secretKey = "Adfaoidalksdhcpxzjhpdhfpdkoxaodfid";
    private long validTokenTime = 30 * 60 * 1000L; // 토큰 유효시간 10분으로 설정 , (테스트를 수월하게 하기위해 30분으로 재설정.)
    private final MemberDetailsService memberDetailsService;
    @Autowired
    public JwtProvider(MemberDetailsService memberDetailsService){
        this.memberDetailsService=memberDetailsService;
    }


    /*
        시크릿키를 인코딩한 뒤 결과값을 String형태로 반환하여 시크릿키에 저장.
        인코딩을 할 때 바이트로 변환하지 않으면 아스키코드형태로 해석되어 인코딩이 이루어지기 때문에
        바이트로 변환한 값을 인코딩해주어야한다.
    */
    @PostConstruct // 객체 의존설정이 끝난 뒤 자동으로 실행
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /*
        토큰을 생성하는 메소드로 로그인이 정상적으로 완료되면 수행된다.
        Claims객체는 claim를 key-value쌍으로 해시맵에 저장하는 객체이다.
        로그인이 정상적으로 수행되면 username만 토큰에 저장한 뒤 클라이언트로 보내준다.password는 보안상 jwt에 저장하지 않는다.
        클라이언트는 인증이 필요한 request를 보낼때 토큰을 같이 보내면 된다. 그러면 별도의 인증과정을 거치지않고 토큰의 유효여부,조작여부만 검사한다.
     */
    public String createToken(String username, List<GrantedAuthority> authorities) {
        log.info("토큰 생성중...........................................");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", authorities);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // claim 저장
                .setIssuedAt(now) // 토큰 발행시간 저장
                .setExpiration(new Date(now.getTime() + validTokenTime)) // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 암호화 알고리즘과, secret 값
                .compact();
    }
    /*
        토큰에서 인증정보를 가져오는 메소드.
        토큰을 바탕으로 UsernamePasswordAuthenticationToken을 생성하고 SprintContextHolder에 저장해야한다.
        따라서 토큰에서 username을 추출한 뒤 이 값으로 MemberUserDetails객체를 생성한다.
        그 뒤 MemberUserDetails객체와 Authority를 통해 UsernamePasswordAuthenticationToken를 생성해 반환한다.
     */
    public Authentication getAuthentication(String token) {
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(memberDetails.getUsername(), memberDetails.getPassword(), memberDetails.getAuthorities());
    }
    /*
        토큰을 받아 sub(username)값을 추출하는 메소드.
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    /*
        토큰의 조작여부와 유효여부를 판단하는 메소드.
        claims.getBody().getExpiration().before(new Date())를 통해 유효여부를 판단한다.
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)를 통해 조작여부를 판단한다.
        만약 조작이 의심되면 예외를 던진다. (+ parser()는 deprecated 됨)
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Exception during token validation: " + e.getMessage(), e);
            return false;
        }
    }
    // request 헤더에서 토큰을 가져오는 메소드.
    public String resolveToken(HttpServletRequest request) {
        // request의 헤더에 Authorization값을 읽어오기
        return request.getHeader("Authorization");
    }
}

