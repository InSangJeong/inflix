package kr.co.insang.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class RESTService {

    private String pathCMS;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    @Autowired
    public RESTService(){
        //나중에 파일로 받도록 변경.
        //pathCMS="http://localhost:9001/cms";
        pathCMS="http://192.168.0.100:9001/cms";

        this.restTemplate = new RestTemplate();
        //this.headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccessControlAllowCredentials(true);
    }

    public boolean requestDeleteAllHistorybyUserid(String userid){
        try{
            String requestPath = pathCMS + "/allhistory/" + userid;

            //여기 exception...
            restTemplate.delete(requestPath);

            return true;
        }catch (Exception e){
            System.out.println("Error Here");
            return false;
        }

    }

}
