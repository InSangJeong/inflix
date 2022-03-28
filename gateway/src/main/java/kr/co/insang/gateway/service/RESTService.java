package kr.co.insang.gateway.service;

import kr.co.insang.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class RESTService {

    //임시로 사용
    private String pathLogin;//인증서버로 로그인요청
    private String pathUser;//인증서버로 유저정보요청(Refresh토큰으로 Acc만들때 사용)

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public RESTService(){
        // 내가 나 자신에게 보는 형태인데 메시지가 어디에선가 쌓여서 메모리 누수 나는듯. SCG에서 그런부분 있는지 확인하고 변경해야함.
        //this.pathLogin="http://10.0.1.168:8000/authen/login";
        //this.pathUser = "http://10.0.1.168:8000/authen/user/{userid}";
        this.pathLogin="http://10.0.1.168:9000/authen/login";
        this.pathUser = "http://10.0.1.168:9000/authen/user/{userid}";


        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    //login server에게 회원정보 요청.(POST)
    public UserDTO getLogin(UserDTO userDto) {
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDto, headers);
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(this.pathLogin, requestEntity, UserDTO.class);

        if(response.hasBody())
            return response.getBody();
        else
            return null;

    }
    //login server에게 회원정보 요청.(get)
    public UserDTO getUser(String id) {

        //여기 getUserid가 계속 바뀌면서 메모리 누수 발생의심됨.
        //ResponseEntity<UserDTO> response = restTemplate.getForEntity(this.pathUser+userDto.getUserid(),  UserDTO.class);
        //아래 내용을 참고하여 수정.
        //https://techblog.woowahan.com/2628/

        ResponseEntity<UserDTO> response = restTemplate.getForEntity(this.pathUser,  UserDTO.class, id);

        if(response.hasBody()){
            return response.getBody();
        }
        else{
            return null;
        }


    }

}
