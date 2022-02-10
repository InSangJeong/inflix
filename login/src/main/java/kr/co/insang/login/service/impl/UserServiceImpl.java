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
        User user = userdto.toEntity();
        User result = userRepo.save(user);
        return result != null;
    }

    @Override
    public boolean CheckID(String user_id){
        Optional<User> user = userRepo.findById(user_id);
        return user.isPresent();
    }

    @Override
    public UserDTO GetUser(String user_id, String password) {
        Optional<User> user = userRepo.findById(user_id);
        if (user.isPresent()) {
            if(user.get().toDTO().getPassword().equals(password))
                return user.get().toDTO();
            else
                return null;
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
