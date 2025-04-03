package studentteacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import studentteacher.model.StudentTeacher;

@Repository
public interface StudentTeacherRepository extends JpaRepository<StudentTeacher,Long> {


}
