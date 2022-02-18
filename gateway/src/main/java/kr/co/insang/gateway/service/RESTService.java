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
    private String loginServerIP;

    @Autowired
    public RESTService(){
         this.loginServerIP = "http://localhost:8000/authen/login";

    }



    //login server에게 회원정보 요청.
    public UserDTO getUserDTO(UserDTO userDto) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> map = new HashMap<>();
        map.put("userid", userDto.getUserid());
        map.put("password", userDto.getPassword());
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<UserDTO> response = restTemplate.postForEntity(this.loginServerIP, entity, UserDTO.class);
        UserDTO result = response.getBody();
        return result;

        //return result.getBody();
        //return null;//등록되지 않은 유저.
    }

}
