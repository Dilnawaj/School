package com.school.controller;

import com.school.entity.Teacher;
import com.school.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Create a new teacher
    @PostMapping
    public ResponseEntity<?> createTeacher(@Valid @RequestBody Teacher teacher) {
        try {
            Teacher savedTeacher = teacherService.createTeacher(teacher);
            return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Get all teachers with their students
    @GetMapping("/with-students")
    public ResponseEntity<List<Teacher>> getAllTeachersWithStudents() {
        List<Teacher> teachers = teacherService.getAllTeachersWithStudents();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Get teacher by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // Get teacher by email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getTeacherByEmail(@PathVariable String email) {
        Optional<Teacher> teacher = teacherService.getTeacherByEmail(email);
        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Teacher not found with email: " + email, HttpStatus.NOT_FOUND);
        }
    }

    // Get teachers by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Teacher>> getTeachersByDepartment(@PathVariable String department) {
        List<Teacher> teachers = teacherService.getTeachersByDepartment(department);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Get teachers by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Teacher>> getTeachersBySubject(@PathVariable String subject) {
        List<Teacher> teachers = teacherService.getTeachersBySubject(subject);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Search teachers by name
    @GetMapping("/search")
    public ResponseEntity<List<Teacher>> searchTeachersByName(@RequestParam String name) {
        List<Teacher> teachers = teacherService.searchTeachersByName(name);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Get teachers by department with students
    @GetMapping("/department/{department}/with-students")
    public ResponseEntity<List<Teacher>> getTeachersByDepartmentWithStudents(@PathVariable String department) {
        List<Teacher> teachers = teacherService.getTeachersByDepartmentWithStudents(department);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // Update teacher
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @Valid @RequestBody Teacher teacherDetails) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacherDetails);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete teacher
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Check if teacher exists
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkTeacherExists(@PathVariable Long id) {
        boolean exists = teacherService.existsById(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Get teacher count by department
    @GetMapping("/department/{department}/count")
    public ResponseEntity<Long> getTeacherCountByDepartment(@PathVariable String department) {
        long count = teacherService.getTeacherCountByDepartment(department);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
