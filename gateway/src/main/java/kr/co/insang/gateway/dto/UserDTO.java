package kr.co.insang.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String user_id;
    private String password;
    private String nickname;
    private String email;
    private String signupday;
    private String grade;



}
