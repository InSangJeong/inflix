package kr.co.insang.gateway.service;

import kr.co.insang.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Service
public class RESTService {
    //@Value("${loginserver}")
    private String loginSeverIP;

    //login server에게 회원정보 요청.
    public UserDTO GetUserDTO(String user_id, String password) {

        URI uri = UriComponentsBuilder
                .fromUriString("loginSeverIP")
                .path("/authen/user")
                .queryParam("user_id", user_id)
                .queryParam("password", password)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate(); //TODO : 구조 바꿔야함.
        ResponseEntity<UserDTO> result = restTemplate.getForEntity(uri, UserDTO.class);
        return result.getBody();
        //return null;//등록되지 않은 유저.
    }

}
