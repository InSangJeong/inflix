package kr.co.insang.login.service.impl;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.entity.User;
import kr.co.insang.login.repository.UserRepository;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public boolean CreateUser(UserDTO userdto) {
        User user = userdto.toEntity();
        User result = userRepo.save(user);
        return result != null;
    }

    @Override
    public boolean UpdateUser(UserDTO userdto){
        //먼저 요청한사람과 요청내용이맞는지 중복확인 필요.

        //확인되면 저장
        User user = userdto.toEntity();
        User result = userRepo.save(user);
        return result != null;
    }

    @Override
    public UserDTO GetUser(String user_id) {
        Optional<User> user = userRepo.findById(user_id);
        if (user.isPresent()) {
            //return userDTO.orElse(null); 형식으로 쓰고싶은데...
            return user.get().toDTO();
        }
        else
            return null;
    }


    @Override
    public boolean DeleteUser(String user_id) {
        if(!userRepo.findById(user_id).isPresent())//없는 유저면 실패.
            return false;
        else
        {
            userRepo.deleteById(user_id);
            return true;
        }

    }

}
