package kr.co.insang.gateway.controller;
/*
import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.UserDTO;
import kr.co.insang.gateway.service.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
*/

import kr.co.insang.gateway.constant.JwtType;
import kr.co.insang.gateway.dto.TokenDTO;
import kr.co.insang.gateway.dto.UserDTO;
import kr.co.insang.gateway.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.co.insang.gateway.service.RESTService;

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


    @PostMapping("/tokens")
    public TokenDTO getTokens(@RequestBody UserDTO userDto) throws Exception {
        UserDTO userinfo = restService.getUserDTO(userDto);
        TokenDTO tokeninfo = new TokenDTO();

        if(userinfo != null){

            String refreshToken = jwtService.makeRefreshToken(userinfo);
            //"Bearer "는 Auth Value에 기본적으로 앞에 붙는데 둘다 동시에 생성할때는 안붙으니까 일단 임시로 이렇게 넣어둠...
            String accessToken = jwtService.makeAccessToken(userinfo, "Bearer "+ refreshToken);

            if(accessToken=="invalid"){
                tokeninfo.setWarn("invalid token.");
            }
            else{
                tokeninfo.setRefreshToken(refreshToken);
                tokeninfo.setAccessToken(accessToken);
                tokeninfo.setWarn("");
            }
        }
        return tokeninfo;
    }

}
