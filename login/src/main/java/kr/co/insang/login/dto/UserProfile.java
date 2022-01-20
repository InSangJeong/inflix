package kr.co.insang.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {

    private String id;
    private String password;
    private String nickname;
    private String email;
    private String signupday;



}
