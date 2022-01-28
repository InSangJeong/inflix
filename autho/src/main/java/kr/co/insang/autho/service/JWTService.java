package kr.co.insang.autho.service;

import kr.co.insang.autho.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    //@Value("secretKey")
    private String secretKey;

    public String makeJwtToken() {
        /*
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                .setIssuer("insang")                            // 토큰발급자 설정
                .setIssuedAt(now)                               //토큰 발급 시간(현재)
                .setAudience("userid")                          //사용자 계정명 입력예정.
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) //토큰 만료시간(30분)
                .claim("grade", "user")             //관리자 및 유저등급 나눌때 사용할 클레임.
                .signWith(SignatureAlgorithm.HS256, secretKey)   // 해싱 알고리즘 + 키
                .compact();
        */
        return "";
    }


    public String makeJwtToken(UserDTO userDTO){
        return "temp";
    }
}
