package kr.co.insang.gateway.service;

import io.jsonwebtoken.*;
import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@PropertySource("classpath:application-jwt.properties")
public class JWTService {
    // Access, Refresh Key는 사용자마다 다르게 설정주는 것이 좋다. 보통 페이로드의 이메일등을 활용.
    @Value("${accessSecretKey}")
    private String accessKey;
    @Value("${refreshSecretKey}")
    private String refreshKey;

    private RESTService restService;
    private Date now;
    //refresh token은 내용 볼 수 없도록 대칭키로 암호화해서 관리.
    //private AES256 aes256; (추후 구현)
    //@Autowired
    //public JWTService(AES256 aes256){this.aes256=aes256;}

    public JWTService(RESTService restService){
        this.restService = restService;
        this.now = new Date();
    }


    //토큰 검증만 수행
    public boolean verifyToken(String token, JwtType type) throws Exception {
        if(type==JwtType.ACCESS)
        {
            return checkClaim(token, accessKey.getBytes());
            //pureToken = token;
        }
        else if(type==JwtType.REFRESH)
        {
            return checkClaim(token, refreshKey.getBytes());
        }
        else{
            return false;
        }
    }

    //토큰 검증후 맞다면 페이로드를 리턴.
    public Claims getPayload(String token, JwtType type){
        if(type==JwtType.ACCESS)
            return getClaim(token, accessKey.getBytes());
        else
            return getClaim(token, refreshKey.getBytes());
    }
    //엑세스 토큰 검증 후 유효하지않으면 403(Forbidden). 리프레시토큰으로 엑세스토큰 발급은 클라이언트에게 다시 요청 받는 로직으로 수행.
    //만약 유효한 경우면 토큰 발급자가 요청한 내용이 발급자에대한 내용인지 판단후 아니면 401(Unauthorized)
    //둘다 맞는경우 200(Ok) 반환.
    //ps. 원래 리프레시토큰은 필요할때만 클라이언트가 보내서 발급과정을 진행해야하는데 xss 방어를 위해서 httponly cookie를
    //    사용하고있는 상태라 client에서 상황에 맞게 쿠키를 보내줄수가없는 상태... 매 요청마다 두 토큰이 같이 왔다갔다함.
    //    구조가 맘에 안드는데 다른사람들은 어떻게 적용했는지 사례를 보고 바꿀 필요가 있어보임. 일단 이대로 진행.
    public int isValuedToken(ServerHttpRequest reqeust) {
        try {


            //쿠키에 토큰이 있는지 확인
            HttpCookie accessCookie = reqeust.getCookies().getFirst("accessToken");
            if(accessCookie!=null){
                //사용자별로 사용할수 있는 서비스를 구분하고 해당 서비스에서 할수있는 권한인지 확인해야하는데
                //서비스 수가 많지않으니까 모든서비스에서 자기자신에게 해당하는 요청인지만 확인.
                //토큰(페이로드의 aud)의 요청자와 요청내용이 같은 사람인지 확인한다.
                //1. url 마지막 요청자 (Get 요청이면서 링크에 ?가 포함된경우로..)
                //2. json 형식의 body에 userid (1번 외)

                Claims claims = getPayload(accessCookie.getValue(), JwtType.ACCESS);
                if(claims!= null){
                    String requestor = claims.getAudience();    //요청자(토큰)
                    if(requestor==null)
                        return 403;//aud항목이 없으므로 잘못된 토큰임.

                    if(reqeust.getMethod().matches("GET") && reqeust.getPath().toString().contains("?")){
                        int numberOfLastPath = reqeust.getPath().elements().size() - 1;
                        if(numberOfLastPath > 2){
                            if(requestor.equals(reqeust.getPath().subPath(numberOfLastPath).value()))
                                return 200;
                        }
                    }
                    else{
                        // Json 타입의 Body에서 userid 값 받아오는 코드 삽입.
                        //Flux<DataBuffer> buffer = reqeust.getBody();

                        //buffer에서 데이터 찾는게 쉽지 않아서 시간이 많이 걸릴듯함. 일단 통과시켜놓고 나중에 추가해야할듯.
                        return 200;
                    }
                    return 401;
                }
                else{
                    return 403;//클레임이 없으므로 잘못된 토큰.
                }
            }
            else{
                //토큰이 없으므로 403 반환
                return 403;
            }
        }catch (Exception e){
            //혹시 모르니까 202 (Accepted)로 반환하고 원인 찾아서해결.
            return 202;
        }
        //유효한 토큰이며 요청자와 요청내용이 일치.
        //return 200;
    }


    public String makeAccessToken(String refreshToken) throws Exception {
        if(verifyToken(refreshToken, JwtType.REFRESH))
        {
            // 1. Refresh 토큰에서 사용자 정보 생성
            Claims claims = getPayload(refreshToken, JwtType.REFRESH);

            if(claims.getAudience()!=null){
                //2. 인증서버로부터 Access토큰에 넣을 값을 받아온다.
                UserDTO  userDTO = restService.getUser(claims.getAudience());

                //몇가지 데이터가 잘 들어왔는지만 확인.
                if(!userDTO.getUserid().isEmpty()
                        && !userDTO.getGrade().isEmpty()
                        && !userDTO.getNickname().isEmpty())

                {
                    //3. Access토큰 생성 및 반환

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
            }
        }
        return "invalid";
    }
    public String makeRefreshToken(UserDTO userDTO) throws Exception {
        try {
            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   //헤더 타입. jwt
                    .setIssuer("insang")                            // 토큰발급자 설정
                    .setIssuedAt(now)                               //토큰 발급 시간(현재)
                    .setAudience(userDTO.getUserid())              //사용자 계정명
                    .setExpiration(new Date(now.getTime() + Duration.ofMinutes(JwtType.ACCESS.getTime()).toMillis())) //토큰 만료시간
                    .claim("grade", userDTO.getGrade())         //관리자 및 유저등급 나눌때 사용할 클레임.
                    .claim("type", "REFRESH")
                    .signWith(SignatureAlgorithm.HS256, refreshKey.getBytes())   // 해싱 알고리즘 + 키
                    .compact();
        }catch (Exception e){
            return null;
        }
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
    public Claims getClaim(String jwt, byte[] key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        }catch(ExpiredJwtException e) {   //Token이 만료된 경우 Exception이 발생한다.
            //logger.error("Token Expired");
            return null;

        }catch(JwtException e) {        //Token이 변조된 경우 Exception이 발생한다.
            //logger.error("Token Error");
            return null;
        }
    }
}
