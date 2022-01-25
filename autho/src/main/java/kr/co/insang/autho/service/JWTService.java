package kr.co.insang.autho.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class JWTService {

    public String makeJwtToken() {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                .setIssuer("insang")                            // 토큰발급자 설정
                .setIssuedAt(now)                               //토큰 발급 시간(현재)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) //토큰 만료시간(30분)
                .claim("id", "아이디")                //비공개 클래임 설정 키-벨류
                .claim("email", "ajufresh@gmail.com")   
                .signWith(SignatureAlgorithm.HS256, "secret")   // 해싱 알고리즘 + 키
                .compact();
    }
}
