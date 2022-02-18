package kr.co.insang.gateway.service;

import io.jsonwebtoken.*;
import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Service
@PropertySource("classpath:application-jwt.properties")
public class JWTService {
    @Value("${accessSecretKey}")
    private String accessKey;
    @Value("${refreshSecretKey}")
    private String refreshKey;
    //refresh token은 내용 볼 수 없도록 대칭키로 암호화해서 관리.
    //private AES256 aes256; (추후 구현)
    //@Autowired
    //public JWTService(AES256 aes256){this.aes256=aes256;}

    public boolean verifyToken(String token, JwtType type) throws Exception {
        String key=null;//access or refresh token key.
        //String pureToken=null;


        // 넘어올때 앞에 Bearer이 붙어서오기 때문에 이부분 확인 후 제거.
        //if(validationAuthorizationHeader(token)){
        //    pureToken = extractToken(token);
        if(type==JwtType.ACCESS)
        {
            key = accessKey;
            //pureToken = token;
        }
        else if(type==JwtType.REFRESH)
        {
            key = refreshKey;
        }
        return checkClaim(token, key.getBytes());

            // 만료시간, 시그니처 확인.
            //return checkClaim(pureToken, key.getBytes());
            //추가로 확인하고싶으면 아래처럼 파싱해서 확인.
            //Claims claims = Jwts.parser()
            //        .setSigningKey(key)
            //        .parseClaimsJws(pureToken)
            //        .getBody();

        //}

    }

    public String makeAccessToken(UserDTO userDTO, String refreshToken) throws Exception {

        if(verifyToken(refreshToken, JwtType.REFRESH))
        {

            //사용자 인증 정보 및 권한
            Date now = new Date();
            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                    .setIssuer("insang")                            // 토큰발급자 설정
                    .setIssuedAt(now)                               //토큰 발급 시간(현재)
                    .setAudience(userDTO.getUserid())              //사용자 계정명
                    .setExpiration(new Date(now.getTime() + Duration.ofMinutes(JwtType.ACCESS.getTime()).toMillis())) //토큰 만료시간
                    .claim("grade", userDTO.getGrade())
                    .claim("type","ACCESS")
                    .claim("nickname", userDTO.getNickname())
                    .signWith(SignatureAlgorithm.HS256, accessKey.getBytes())  // 해싱 알고리즘 + 키
                    .compact();
        }
        else
        {
            return "invalid";
        }


    }
    public String makeRefreshToken(UserDTO userDTO) throws Exception {

        Date now = new Date();
        String refreshtoken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                .setIssuer("insang")                            // 토큰발급자 설정
                .setIssuedAt(now)                               //토큰 발급 시간(현재)
                .setAudience(userDTO.getUserid())              //사용자 계정명
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(JwtType.REFRESH.getTime()).toMillis())) //토큰 만료시간
                .claim("grade", userDTO.getGrade())         //관리자 및 유저등급 나눌때 사용할 클레임.
                .claim("type", "REFRESH")
                .signWith(SignatureAlgorithm.HS256, refreshKey.getBytes())   // 해싱 알고리즘 + 키
                .compact();
        return refreshtoken;
    }
    private boolean validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer "))
            return false;

        return true;
    }
    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    public boolean checkClaim(String jwt, byte[] key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
            return true;
        }catch(ExpiredJwtException e) {   //Token이 만료된 경우 Exception이 발생한다.
            //logger.error("Token Expired");
            return false;

        }catch(JwtException e) {        //Token이 변조된 경우 Exception이 발생한다.
            //logger.error("Token Error");
            return false;
        }
    }
}
