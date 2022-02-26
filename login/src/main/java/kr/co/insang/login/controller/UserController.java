package kr.co.insang.login.controller;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.service.RESTService;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authen")
public class UserController {
    //토큰 없이 접근 가능(checkID, createUser) 그 외에는 토큰 있는경우에만 접근가능.
    //직접 접근하지 못하도록 Gateway만 통신 가능하도록 방화벽 설정하고 Gateway에서도 인가로직 거칠예정.

    private UserService userService;
    private RESTService restService;

    @Autowired
    public UserController(UserService userService, RESTService restService){
        this.userService = userService;
        this.restService = restService;
    }

    //중복계정 확인.
    @GetMapping("/user")
    public ResponseEntity<Boolean> checkID(@RequestParam("userid") String userid){
        try{
            if(userService.CheckID(userid)){
                //중복계정 있음.
                return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
            }else{
                //중복계정 없음.
                return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
    }
    //회원 가입후 가입 성공 여부 반환.
    @PostMapping("/user")
    public ResponseEntity<Boolean> createUser(@RequestBody UserDTO user){
        try{
            userService.CreateUser(user);
            if(userService.CheckID(user.getUserid())){
                //가입성공
                return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
            }
            else{
                //가입실패(디버깅해봐야함)
                return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<UserDTO> getUserinfo(@RequestBody UserDTO user){
        try {
            UserDTO userDTO = userService.GetUser(user);
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("로그인실패 GetUser에서 DTO를 못받음.");
            return new ResponseEntity<UserDTO>(HttpStatus.OK);
        }

    }
    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){
        UserDTO changedUser = userService.UpdateUser(user);
        if(changedUser!=null)
            return new ResponseEntity<UserDTO>(changedUser, HttpStatus.OK);
        else
            return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestBody UserDTO user){
        try{
            //1. 비번,아이디 확인.
            UserDTO checkPass = userService.GetUser(user);
            if(checkPass==null)
                return new ResponseEntity<String>("Wrong User info.", HttpStatus.ACCEPTED);
            //2. 연계된 서비스에게 id를 넘겨주면서 삭제요청
            // ps DB가 나눠져있었으면 2번처럼 하려고했는데 테이블로만 나눠진거라 CASCADE로 처리함.
            //if(restService.requestDeleteAllHistorybyUserid(user.getUserid())){
            //3. 모두완료시 회원정보삭제
            userService.DeleteUser(user.getUserid());
            //4. 결과 반환.
            return new ResponseEntity<String>("Success", HttpStatus.OK);
            //}
            //System.out.println("fail to delete histories.");
            //return new ResponseEntity<String>(HttpStatus.ACCEPTED);

        }catch (Exception e){
            return new ResponseEntity<String>(e.toString(), HttpStatus.ACCEPTED);
        }
    }

}
