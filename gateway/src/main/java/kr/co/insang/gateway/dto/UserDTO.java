package kr.co.insang.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String userid;
    private String password;
    private String nickname;
    private String email;
    private String signupday;
    private String grade;

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("password", password);
        return map;
    }


}
