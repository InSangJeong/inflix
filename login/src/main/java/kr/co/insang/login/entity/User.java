package kr.co.insang.login.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {// implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityid;

    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String signupday;

}
