package kr.co.insang.login.controller;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authen")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //중복계정 확인.
    @GetMapping("/user/{id}")
    public boolean checkID(@PathVariable String id){
            return  userService.CheckID(id);
    }

    //로그인 정보 확인.
    @PostMapping("/login")
    public UserDTO getUserinfo(@RequestBody UserDTO user){
        UserDTO userDTO = userService.GetUser(user);
        return userDTO;
    }

    @PostMapping("/user")
    //회원 가입후 가입 성공 여부 반환.
    public boolean createUser(@RequestBody UserDTO user){
        userService.CreateUser(user);
        return userService.CheckID(user.getUser_id());
    }

    @PutMapping("/user")
    public UserDTO updateUser(@RequestBody UserDTO user){
        if(userService.UpdateUser(user))
            return userService.GetUser(user);
        else
            return null;
    }
    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable("id")String id){
        return userService.DeleteUser(id);
    }

}
