package kr.co.insang.login.entity;

import javax.persistence.*;

import kr.co.insang.login.dto.UserDTO;
import lombok.*;

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
        this.signupday = signupday;
        this.grade = grade;
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
