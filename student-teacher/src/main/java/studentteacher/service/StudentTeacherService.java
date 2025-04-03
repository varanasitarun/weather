package studentteacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentteacher.model.StudentTeacher;
import studentteacher.repository.StudentTeacherRepository;
@Service
public class StudentTeacherService {

    @Autowired
    private StudentTeacherRepository studentTeacherRepository;

    @Transactional
    public StudentTeacher assignStudentToTeacher(StudentTeacher studentTeacher) {
        return studentTeacherRepository.save(studentTeacher);
    }
}
