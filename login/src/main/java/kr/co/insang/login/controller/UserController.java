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

    //회원정보를 보여주는 기능
    //없는 회원일 경우 null, 있는 회원일 경우 회원 정보를 json형태로 전달.
    @GetMapping("/user/{id}")
    public UserDTO getUserinfo(@PathVariable("id")String id){
            UserDTO userDTO = userService.GetUser(id);
            return userDTO;
    }
    /*
    @GetMapping("/user")
    public List<UserDTO> getAllUserinfo(){
        List<UserDTO> userDTOs = userService.GetAllUser();
        return userDTOs;
    }*/

    @PostMapping("/user")
    public UserDTO createUser(@RequestBody UserDTO user){
        if(userService.CreateUser(user))
            return userService.GetUser(user.getUser_id());
        else
            return null;
    }

    //아래 두개 기능 추가.
    @PutMapping("/user")
    public UserDTO updateUser(@RequestBody UserDTO user){
        if(userService.UpdateUser(user))
            return userService.GetUser(user.getUser_id());
        else
            return null;
    }
    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable("id")String id){
        return userService.DeleteUser(id);
    }

}
