package kr.co.insang.gateway.controller;

import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.UserDTO;
import kr.co.insang.gateway.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import kr.co.insang.gateway.service.RESTService;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/autho")
public class JWTcontroller {
    private RESTService restService;
    private JWTService jwtService;

    @Autowired
    public JWTcontroller(RESTService restService, JWTService jwtService){
        this.restService = restService;
        this.jwtService = jwtService;
    }

    //get Access, Refresh Tokens.
    @PostMapping("/tokens")
    public Mono<String> getTokens(@RequestBody UserDTO userDto, ServerWebExchange exchange) throws Exception {
        UserDTO userinfo = restService.getUserDTO(userDto);

        if(userinfo != null){

            String refreshToken = jwtService.makeRefreshToken(userinfo);
            //"Bearer "는 Auth Value에 기본적으로 앞에 붙는데 둘다 동시에 생성할때는 안붙으니까 일단 임시로 이렇게 넣어둠...
            String accessToken = jwtService.makeAccessToken(userinfo, refreshToken);

            if(accessToken=="invalid"){
                return Mono.just("Fail");//return Mono.just("invalid token.");
            }
            else{

                ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",refreshToken)
                                .httpOnly(true).path("/").domain("localhost").maxAge(60).build();
                ResponseCookie accessCookie = ResponseCookie.from("accessToken",accessToken)
                        .httpOnly(true).path("/").domain("localhost").maxAge(60).build();

                                //.secure(true)


                exchange.getResponse().addCookie(refreshCookie);
                exchange.getResponse().addCookie(accessCookie);

                //return Mono.just("Success");
                return Mono.just(userinfo.getUserid());
            }
        }
        //return ServerResponse.ok()
        //        .contentType(MediaType.APPLICATION_JSON)
        //        .body(Mono.just("Fail."), String.class);
        return Mono.just("Fail");//return Mono.just("Wrong User Info.");
    }

    @GetMapping("/loginstate")
    public Mono<String> getLoginState(ServerWebExchange exchange) throws Exception {

        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();

        HttpCookie accessCookie = cookies.getFirst("accessToken");
        HttpCookie refreshCookie = cookies.getFirst("refreshToken");

        if(accessCookie!=null){
            if(jwtService.verifyToken(accessCookie.getValue(), JwtType.ACCESS)){
                //유효한 Access Token인 경우.
                return Mono.just("Ok");
            }
            else{
                //Access Token이 유효하지 않으면 Refresh Token이 있는지 확인하여 발급한다.
                if(refreshCookie!=null){

                }
            }
        }


        ServerHttpResponse response = exchange.getResponse();

        return Mono.just("Ok");
    }
}
