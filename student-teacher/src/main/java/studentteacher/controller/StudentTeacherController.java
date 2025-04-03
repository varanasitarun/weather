package studentteacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import studentteacher.model.StudentTeacher;
import studentteacher.service.StudentTeacherService;

@RestController
@RequestMapping("/student-teacher")
public class StudentTeacherController {

    @Autowired
    private StudentTeacherService studentTeacherService;

    @PostMapping
    public ResponseEntity<StudentTeacher> assignStudentToTeacher(@RequestBody StudentTeacher studentTeacher) {
        return ResponseEntity.ok(studentTeacherService.assignStudentToTeacher(studentTeacher));
    }


}
