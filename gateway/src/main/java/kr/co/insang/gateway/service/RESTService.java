package kr.co.insang.gateway.service;

import kr.co.insang.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class RESTService {
    //@Value("${loginserver}")
    //private String loginSeverIP;

    //임시로 사용
    private String gatewayServer;
    private String pathLogin;//인증서버로 로그인요청
    private String pathUser;//인증서버로 유저정보요청(Refresh토큰으로 Acc만들때 사용)
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @Autowired
    public RESTService(){
        this.gatewayServer = "http://localhost:8000";

        this.pathLogin="/authen/login";
        this.pathUser = "/authen/user/";


        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    //login server에게 회원정보 요청.(POST)
    public UserDTO getLogin(UserDTO userDto) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(userDto.toMap(), headers);
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(this.gatewayServer+this.pathLogin, entity, UserDTO.class);
        if(response.hasBody())
            return response.getBody();
        else
            return null;
    }
    //login server에게 회원정보 요청.(get)
    public UserDTO getUser(UserDTO userDto) {
        String baseUrl = this.gatewayServer+this.pathUser+userDto.getUserid();
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(baseUrl,  UserDTO.class);
        if(response.hasBody())
            return response.getBody();
        else
            return null;
    }

}
