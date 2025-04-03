package studentteacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studentteacher.model.Teacher;
import studentteacher.service.TeacherService;

import java.util.List;


@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {

        return ResponseEntity.ok(teacherService.saveTeacher(teacher));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        // Call the service to delete the teacher by id
        boolean isDeleted = teacherService.deleteTeacherById(id);

        if (isDeleted) {
            // Return 204 No Content if the teacher was deleted successfully
            return ResponseEntity.noContent().build();
        } else {
            // Return 404 Not Found if the teacher does not exist
            return ResponseEntity.notFound().build();
        }
    }


}
