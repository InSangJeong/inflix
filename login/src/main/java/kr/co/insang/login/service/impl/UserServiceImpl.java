package kr.co.insang.login.service.impl;

import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.entity.User;
import kr.co.insang.login.repository.UserRepository;
import kr.co.insang.login.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    Logger logger;
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
    public UserDTO UpdateUser(UserDTO userDto){
        try{
            if(userDto.getUserid()!=null && userDto.getPassword()!=null)
            {
                Optional<User> user = userRepo.findById(userDto.getUserid());
                if (user.isPresent()) {
                    UserDTO beforeUser = user.get().toDTO();
                    if (beforeUser.getPassword().equals(userDto.getPassword())) {//ID,PW가 맞으면
                        beforeUser.updateUser(userDto);//chPW, nickname, email 중 null이 아닌 것만 수정함.
                        User afterUserEntity = userRepo.save( beforeUser.toEntity());
                        UserDTO afterUserDto = afterUserEntity.toLoginDTO();
                        return afterUserDto;
                    }
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean CheckID(String user_id){
        Optional<User> user = userRepo.findById(user_id);
        return user.isPresent();
    }

    @Override
    public UserDTO GetUser(UserDTO userdto) {
        try{
            Optional<User> user = userRepo.findById(userdto.getUserid());
            if (user.isPresent()) {
                //Gateway에서 온 요청은 비밀번호를 요구X
                if(user.get().toDTO().getPassword().equals(userdto.getPassword()))
                    return user.get().toLoginDTO();
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public UserDTO GetUser(String user_id){
        Optional<User> user = userRepo.findById(user_id);
        if (user.isPresent()) {
            return user.get().toLoginDTO();
        }
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
