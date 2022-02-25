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
    private String chpassword;
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

    //기존유저의 정보를 after 유저의 정보로 바꾼다.
    //22.2.25기준 변경할수 있는 정보는 비밀번호, 닉네임, 이메일주소만.
    public void updateUser(UserDTO afterUser){
        if(afterUser.getChpassword()!=null)
            this.password = afterUser.chpassword;
        if(afterUser.getNickname()!=null)
            this.nickname = afterUser.nickname;
        if(afterUser.getEmail()!=null)
            this.email = afterUser.email;
    }


}
