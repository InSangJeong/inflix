package kr.co.insang.login.service;

import kr.co.insang.login.dto.UserDTO;

import java.util.List;

public interface UserService {
    //true == success, false == fail
    boolean CreateUser(UserDTO userdto);
    boolean UpdateUser(UserDTO userdto);
    UserDTO GetUser(UserDTO userdto);
    boolean DeleteUser(String user_id);

    boolean CheckID(String user_id);
}