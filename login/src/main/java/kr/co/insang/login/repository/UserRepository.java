package kr.co.insang.login.repository;

import kr.co.insang.login.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
