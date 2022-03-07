package kr.co.insang.login.service.impl;

import kr.co.insang.login.config.WebSecurityConfig;
import kr.co.insang.login.dto.UserDTO;
import kr.co.insang.login.entity.User;
import kr.co.insang.login.repository.UserRepository;
import kr.co.insang.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean CreateUser(UserDTO userdto) {
        UserDTO saveUser = UserDTO.builder()
                .userid(userdto.getUserid())
                .password(passwordEncoder.encode(userdto.getPassword()))
                .nickname(userdto.getNickname())
                .email(userdto.getEmail())
                .grade(userdto.getGrade())
                .signupday(userdto.getSignupday())
                .build();
        User user = saveUser.toEntity();
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

                    if(passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())){
                        UserDTO beforeUser = user.get().toDTO();

                        if(userDto.getChpassword()!=null){
                            userDto.setchPassword(passwordEncoder.encode(userDto.getPassword()));
                        }


                        beforeUser.updateUser(userDto);//chPW, nickname, email 중 null이 아닌 것만 수정함.
                        User afterUserEntity = beforeUser.toEntity();

                        userRepo.save(afterUserEntity);
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
                //if(user.get().toDTO().getPassword().equals(userdto.getPassword()))
                if(passwordEncoder.matches(userdto.getPassword(), user.get().getPassword()))
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
