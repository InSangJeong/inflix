package kr.co.insang.autho.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.insang.autho.constant.JwtType;
import kr.co.insang.autho.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class JWTService {
    @Value("${secretKey}")
    private String secretKey;



    public String makeJwtToken(UserDTO userDTO, JwtType type){

        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                .setIssuer("insang")                            // 토큰발급자 설정
                .setIssuedAt(now)                               //토큰 발급 시간(현재)
                .setAudience(userDTO.getUser_id())              //사용자 계정명
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(type.getTime()).toMillis())) //토큰 만료시간
                .claim("grade", userDTO.getGrade())         //관리자 및 유저등급 나눌때 사용할 클레임.
                .claim("type", type.getType())         //Access Token
                .signWith(SignatureAlgorithm.HS256, "test")   // 해싱 알고리즘 + 키
                .compact();

    }
    public String checkToken(String token){
        //frontend 만들고나서 개발 진행.

        return "";
    }
}
