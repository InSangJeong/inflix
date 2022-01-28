package kr.co.insang.login.controller;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login-api-v1")
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

    //인증정보, 유저 정보 확인.
    @GetMapping("/user")
    public UserDTO getUserinfo(@RequestParam("user_id")String id, @RequestParam("password")String password){
        UserDTO userDTO = userService.GetUser(id, password);
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
            return userService.GetUser(user.getUser_id(), user.getPassword());
        else
            return null;
    }
    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable("id")String id){
        return userService.DeleteUser(id);
    }

}
