package com.school.service;

import com.school.entity.Teacher;
import com.school.repo.TeacherRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class TeacherService {

    @Autowired
    private TeacherRepo teacherRepository;

    // Create a new teacher
    public Teacher createTeacher(Teacher teacher) {
        // Check if email already exists
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Teacher with email " + teacher.getEmail() + " already exists");
        }
        return teacherRepository.save(teacher);
    }

    // Get all teachers
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Get all teachers with their students
    public List<Teacher> getAllTeachersWithStudents() {
        return teacherRepository.findAllWithStudents();
    }

    // Get teacher by ID
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // Get teacher by email
    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    // Get teachers by department
    public List<Teacher> getTeachersByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }

    // Get teachers by subject
    public List<Teacher> getTeachersBySubject(String subject) {
        return teacherRepository.findBySubject(subject);
    }

    // Search teachers by name
    public List<Teacher> searchTeachersByName(String name) {
        return teacherRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    // Update teacher
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (!teacher.getEmail().equals(teacherDetails.getEmail()) &&
                teacherRepository.existsByEmail(teacherDetails.getEmail())) {
            throw new RuntimeException("Teacher with email " + teacherDetails.getEmail() + " already exists");
        }

        teacher.setFirstName(teacherDetails.getFirstName());
        teacher.setLastName(teacherDetails.getLastName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPhoneNumber(teacherDetails.getPhoneNumber());
        teacher.setSubject(teacherDetails.getSubject());
        teacher.setDepartment(teacherDetails.getDepartment());

        return teacherRepository.save(teacher);
    }

    // Delete teacher
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        // Remove teacher reference from all students
        teacher.getStudents().forEach(student -> student.setTeacher(null));

        teacherRepository.delete(teacher);
    }

    // Check if teacher exists
    public boolean existsById(Long id) {
        return teacherRepository.existsById(id);
    }

    // Check if teacher exists by email
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }

    // Get teacher count by department
    public long getTeacherCountByDepartment(String department) {
        return teacherRepository.countByDepartment(department);
    }

    // Get teachers by department with students
    public List<Teacher> getTeachersByDepartmentWithStudents(String department) {
        return teacherRepository.findByDepartmentWithStudents(department);
    }
}
