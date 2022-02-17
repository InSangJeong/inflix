package kr.co.insang.login.dto;

import kr.co.insang.login.entity.User;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String userid;
    private String password;
    private String nickname;
    private String email;
    private String signupday;
    private String grade;


    public User toEntity() {
        return User.builder()
                .userid(userid)
                .password(password)
                .nickname(nickname)
                .email(email)
                .signupday(signupday)
                .grade(grade)
                .build();
    }


}
