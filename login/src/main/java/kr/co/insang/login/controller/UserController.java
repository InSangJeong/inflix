package kr.co.insang.login.controller;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authen")
public class UserController {
    //토큰 없이 접근 가능(checkID, createUser) 그 외에는 토큰 있는경우에만 접근가능.
    //직접 접근하지 못하도록 Gateway만 통신 가능하도록 방화벽 설정하고 Gateway에서도 인가로직 거칠예정.

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //중복계정 확인.
    @GetMapping("/user")
    public boolean checkID(@RequestParam("userid") String userid){
            return  userService.CheckID(userid);
    }
    //회원 가입후 가입 성공 여부 반환.
    @PostMapping("/user")
    public boolean createUser(@RequestBody UserDTO user){
        userService.CreateUser(user);
        return userService.CheckID(user.getUserid());
    }
    //토큰으로 사용자 정보 얻기.(Gateway only)
    @GetMapping("/user/{userid}")
    public UserDTO getUser(@PathVariable("userid") String userid){
        //네트워크 레벨에서 막긴 할테지만 소스코드상으로도 막을수 있는 방법이 있을지 생각해보자..
        UserDTO userDTO = userService.GetUser(userid);
        return userDTO;
    }
    //로그인 매핑.
    @PostMapping("/login")
    public UserDTO getUserinfo(@RequestBody UserDTO user){
        UserDTO userDTO = userService.GetUser(user);
        return userDTO;
    }
    @PutMapping("/user")
    public UserDTO updateUser(@RequestBody UserDTO user){
        if(userService.UpdateUser(user))
            return userService.GetUser(user);
        else
            return null;
    }
    @DeleteMapping("/user/{userid}")
    public boolean deleteUser(@PathVariable("userid") String userid){
        return userService.DeleteUser(userid);
    }

}
