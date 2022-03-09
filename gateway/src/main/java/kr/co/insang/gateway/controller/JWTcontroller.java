package kr.co.insang.gateway.controller;

import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.UserDTO;
import kr.co.insang.gateway.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import kr.co.insang.gateway.service.RESTService;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/autho")
public class JWTcontroller {
    private RESTService restService;
    private JWTService jwtService;
    private String domain;

    @Autowired
    public JWTcontroller(RESTService restService, JWTService jwtService){
        this.restService = restService;
        this.jwtService = jwtService;
        this.domain = "insang.co.kr";
    }

    //get Access, Refresh Tokens.
    //Refresh Token은 로그인을 통해서만 얻을 수 있으므로 아래 매핑으로만 얻어야함.
    @PostMapping("/tokens")
    public Mono<UserDTO> getTokens(@RequestBody UserDTO userDto, ServerWebExchange exchange) throws Exception {
        UserDTO userinfo = restService.getLogin(userDto);

        if(userinfo != null){

            String refreshToken = jwtService.makeRefreshToken(userinfo);
            String accessToken = jwtService.makeAccessToken(refreshToken);

            if(accessToken.equals("invalid")){
                System.out.println("wrong  aToken");
                return Mono.empty();
            }
            else{

                ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",refreshToken)
                                .httpOnly(true).path("/").domain(this.domain).maxAge(JwtType.REFRESH.getTime()).build();
                ResponseCookie accessCookie = ResponseCookie.from("accessToken",accessToken)
                        .httpOnly(true).path("/").domain(this.domain).maxAge(JwtType.ACCESS.getTime()).build();
                                //.secure(true)

                exchange.getResponse().addCookie(refreshCookie);
                exchange.getResponse().addCookie(accessCookie);
                return Mono.just(userinfo);
            }
        }
        return Mono.empty();
    }

    //토큰을 httponly로 관리하니 Front에서 토큰상태를 확인하기가 어려우니 아래 매핑으로 해결.
    @GetMapping("/tokenstate")
    public Mono<String> getTokenState(ServerWebExchange exchange) throws Exception {
        //둘다 없거나 유효하지않으면   00
        //엑세스토큰만 유효하면       10
        //리프레시토큰만 유효하면     01
        //둘다 유효하면             11
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        String result = "";

        HttpCookie accessCookie = cookies.getFirst("accessToken");
        HttpCookie refreshCookie = cookies.getFirst("refreshToken");

        if(accessCookie!=null){
            if(jwtService.verifyToken(accessCookie.getValue(), JwtType.ACCESS))
                result += "1";
            else
                result += "0";
        }else{
            result += "0";
        }
        if(refreshCookie!=null){
            if(jwtService.verifyToken(refreshCookie.getValue(), JwtType.REFRESH))
                result += "1";
            else
                result += "0";
        }else{
            result += "0";
        }

        return Mono.just(result);
    }

    //httponly에 Refresh 토큰이 있으므로 http 요청만으로 Access 토큰 생성을 시도한다.
    //생성 성공시 Success
    //실패시 Fail
    @GetMapping("/accesstoken")
    public Mono<String> getAccessToken(ServerWebExchange exchange) throws Exception{
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie refreshCookie = cookies.getFirst("refreshToken");

        if(refreshCookie!=null) {
            if (jwtService.verifyToken(refreshCookie.getValue(), JwtType.REFRESH)) {
                String accessToken = jwtService.makeAccessToken(refreshCookie.getValue());
                if (!accessToken.equals("invalid")) {//엑세스 토큰이 만들어졌으면 쿠키로 만들어서 httponly로 설정.
                    ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                            .httpOnly(true).path("/").domain(this.domain).maxAge(JwtType.ACCESS.getTime()).build();
                    exchange.getResponse().addCookie(accessCookie);
                    return Mono.just("Success");
                }
            }
        }
        return Mono.just("Fail");
    }

    @DeleteMapping("/tokens")
    //쿠키를 강제로 삭제할수 없으므로 만료시간을 현재로 맞춰 삭제.
    public Mono<Boolean> deleteTokens(ServerWebExchange exchange){
        try{
            ResponseCookie rCookie = ResponseCookie.from("refreshToken","deleted")
                    .httpOnly(true).path("/").domain(this.domain).maxAge(0).build();
            ResponseCookie aCookie = ResponseCookie.from("accessToken","deleted")
                    .httpOnly(true).path("/").domain(this.domain).maxAge(0).build();

            exchange.getResponse().addCookie(rCookie);
            exchange.getResponse().addCookie(aCookie);

            return Mono.just(true);
        }catch(Exception e){
            return Mono.just(false);
        }
    }

}
