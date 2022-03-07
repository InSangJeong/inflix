package kr.co.insang.login.entity;

import javax.persistence.*;

import kr.co.insang.login.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
public class User {// implements Serializable {



    @Id
    @Column(nullable = false)
    private String userid;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String signupday;
    @Column(nullable = false)
    private String grade;

    @Builder
    public User(String userid, String password, String nickname, String email, String signupday, String grade) {
        this.userid = userid;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        if(signupday==null)
            this.signupday = LocalDateTime.now().toString();
        else
            this.signupday = signupday;

        if(grade==null)//일단 USER로 fix.
            this.grade = "USER";
        else
            this.grade = grade;
    }

    public UserDTO toDTO(){
        return UserDTO.builder()
                .userid(this.userid)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .signupday(this.signupday)
                .grade(this.grade)
                .build();
    }
    public UserDTO toLoginDTO(){
        return UserDTO.builder()
                .userid(this.userid)
                .nickname(this.nickname)
                .email(this.email)
                .signupday(this.signupday)
                .grade(this.grade)
                .build();
    }



}
