package studentteacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import studentteacher.model.Users;
@RestController
public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByUsername(String username);

}

//plain ->Hash (SHA/MD5) SHA256 BCRYPT

