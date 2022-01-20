package kr.co.insang.login.controller;

import kr.co.insang.login.dto.UserProfile;
import org.springframework.web.bind.annotation.*;

//임시로 사용
import java.util.Map;

@RestController
public class UserController {
    private Map<String, UserProfile> userMap;

    @GetMapping("/user/{id}")
    public UserProfile getUserProfile(@PathVariable("id")String id){
        return userMap.get(id);
    }

    @PutMapping("/user/{id}")
    public void putUserProfile(@PathVariable("id")String id, @RequestParam("name") String name,@RequestParam("password") String password,
                               @RequestParam("nickname") String nickname,@RequestParam("email") String email)
    {
        UserProfile user = new UserProfile(id,password,nickname,email,"");
        userMap.put(id, user);
    }
    @PostMapping("/user/{id}")
    public void postUserProfile(@PathVariable("id")String id, @RequestParam("name") String name,@RequestParam("password") String password,
                               @RequestParam("nickname") String nickname,@RequestParam("email") String email)
    {
        UserProfile user = userMap.get(id);
        user.setId(id);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setSignupday("");
    }
}
