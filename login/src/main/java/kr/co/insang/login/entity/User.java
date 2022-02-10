package kr.co.insang.login.entity;

import javax.persistence.*;

import kr.co.insang.login.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
public class User {// implements Serializable {

    @Id
    @Column(nullable = false)
    private String user_id;
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
    public User(String user_id, String password, String nickname, String email, String signupday, String grade) {
        this.user_id = user_id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        if(signupday==null)
            this.signupday = LocalDateTime.now().toString();
        else
            this.signupday = signupday;

        if(grade==null)//일단 USER로 fix.
            this.grade = "USER";
    }

    public UserDTO toDTO(){
        return UserDTO.builder()
                .user_id(this.user_id)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .signupday(this.signupday)
                .grade(this.grade)
                .build();
    }

}
