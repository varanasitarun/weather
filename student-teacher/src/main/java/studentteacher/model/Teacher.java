package studentteacher.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    @JoinTable(
            name = "student_teacher", // junction table name
            joinColumns = @JoinColumn(name = "teacher_id"), // teacher column
            inverseJoinColumns = @JoinColumn(name = "student_id") // student column
    )
    private Set<Student> students = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    // Helper method to manage the bidirectional association
    public void addStudent(Student student) {
        this.students.add(student);
        student.getTeachers().add(this);  // Ensure synchronization
    }
}