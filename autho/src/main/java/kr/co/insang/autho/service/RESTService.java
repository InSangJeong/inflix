package kr.co.insang.autho.service;

import kr.co.insang.autho.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Service
public class RESTService {

    //login server에게 회원정보 요청.
    public UserDTO GetUserDTO(String user_id, String password) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9000")
                .path("/login-api-v1/user")
                .queryParam("user_id", user_id)
                .queryParam("password", password)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDTO> result = restTemplate.getForEntity(uri, UserDTO.class);

        return null;//등록되지 않은 유저.
    }

}
