package com.school.controller;

import com.school.entity.Student;
import com.school.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Create a new student
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) {
        try {
            Student savedStudent = studentService.createStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get all students with their teacher information
    @GetMapping("/with-teacher")
    public ResponseEntity<List<Student>> getAllStudentsWithTeacher() {
        List<Student> students = studentService.getAllStudentsWithTeacher();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // Get student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getStudentByEmail(@PathVariable String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found with email: " + email, HttpStatus.NOT_FOUND);
        }
    }

    // Get student by student ID
    @GetMapping("/student-id/{studentId}")
    public ResponseEntity<?> getStudentByStudentId(@PathVariable String studentId) {
        Optional<Student> student = studentService.getStudentByStudentId(studentId);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found with student ID: " + studentId, HttpStatus.NOT_FOUND);
        }
    }

    // Get students by grade level
    @GetMapping("/grade/{gradeLevel}")
    public ResponseEntity<List<Student>> getStudentsByGradeLevel(@PathVariable String gradeLevel) {
        List<Student> students = studentService.getStudentsByGradeLevel(gradeLevel);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get students by teacher ID
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Student>> getStudentsByTeacherId(@PathVariable Long teacherId) {
        List<Student> students = studentService.getStudentsByTeacherId(teacherId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get students without teacher assigned
    @GetMapping("/without-teacher")
    public ResponseEntity<List<Student>> getStudentsWithoutTeacher() {
        List<Student> students = studentService.getStudentsWithoutTeacher();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Search students by name
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudentsByName(@RequestParam String name) {
        List<Student> students = studentService.searchStudentsByName(name);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get students by teacher's subject
    @GetMapping("/teacher-subject/{subject}")
    public ResponseEntity<List<Student>> getStudentsByTeacherSubject(@PathVariable String subject) {
        List<Student> students = studentService.getStudentsByTeacherSubject(subject);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get students enrolled between dates
    @GetMapping("/enrolled-between")
    public ResponseEntity<List<Student>> getStudentsEnrolledBetween(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Student> students = studentService.getStudentsEnrolledBetween(start, end);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Assign teacher to student
    @PutMapping("/{studentId}/assign-teacher/{teacherId}")
    public ResponseEntity<?> assignTeacherToStudent(@PathVariable Long studentId, @PathVariable Long teacherId) {
        try {
            Student updatedStudent = studentService.assignTeacherToStudent(studentId, teacherId);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Remove teacher from student
    @PutMapping("/{studentId}/remove-teacher")
    public ResponseEntity<?> removeTeacherFromStudent(@PathVariable Long studentId) {
        try {
            Student updatedStudent = studentService.removeTeacherFromStudent(studentId);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Check if student exists
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkStudentExists(@PathVariable Long id) {
        boolean exists = studentService.existsById(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Get student count by grade level
    @GetMapping("/grade/{gradeLevel}/count")
    public ResponseEntity<Long> getStudentCountByGradeLevel(@PathVariable String gradeLevel) {
        long count = studentService.getStudentCountByGradeLevel(gradeLevel);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Get student count by teacher
    @GetMapping("/teacher/{teacherId}/count")
    public ResponseEntity<Long> getStudentCountByTeacher(@PathVariable Long teacherId) {
        long count = studentService.getStudentCountByTeacher(teacherId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
