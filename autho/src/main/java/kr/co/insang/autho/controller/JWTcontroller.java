package kr.co.insang.autho.controller;

import kr.co.insang.autho.constant.JwtType;
import kr.co.insang.autho.dto.UserDTO;
import kr.co.insang.autho.service.JWTService;
import kr.co.insang.autho.service.RESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth-jwt-v1")
public class JWTcontroller {

    private JWTService jwtService;
    private RESTService restService;

    @Autowired
    public JWTcontroller(JWTService jwtService, RESTService restService){
        this.jwtService=jwtService;
        this.restService=restService;
    }

    @GetMapping("/token")
    public String getToken(@RequestParam("user_id") String user_id,@RequestParam("password") String password){

        //유저 있는지 인증서버에 확인
        UserDTO userDTO = restService.GetUserDTO(user_id, password);


        if(userDTO == null)
            return "";
        else
        {
            //유저 정보를 토대로 Jwt 생성.
            return jwtService.makeJwtToken(userDTO, JwtType.ACCESS);
//            jwtService.makeJwtToken(userDTO, JwtType.REFRESH);
        }
    }

    //토큰 검증 테스트용..
    @GetMapping("/check")
    public String CheckToken(@RequestParam("token")String token){

        String result = jwtService.checkToken(token);

        return "";
    }

}
