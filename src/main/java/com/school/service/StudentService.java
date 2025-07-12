package com.school.service;

import com.school.entity.Student;
import com.school.entity.Teacher;
import com.school.repo.StudentRepo;
import com.school.repo.TeacherRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StudentService {


    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private TeacherRepo teacherRepository;

    // Create a new student
    public Student createStudent(Student student) {
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with email " + student.getEmail() + " already exists");
        }

        // Check if student ID already exists
        if (student.getStudentId() != null && studentRepository.existsByStudentId(student.getStudentId())) {
            throw new RuntimeException("Student with ID " + student.getStudentId() + " already exists");
        }

        // Set enrollment date if not provided
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());
        }

        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get all students with their teacher information
    public List<Student> getAllStudentsWithTeacher() {
        return studentRepository.findAllWithTeacher();
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Get student by email
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Get student by student ID
    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    // Get students by grade level
    public List<Student> getStudentsByGradeLevel(String gradeLevel) {
        return studentRepository.findByGradeLevel(gradeLevel);
    }

    // Get students by teacher ID
    public List<Student> getStudentsByTeacherId(Long teacherId) {
        return studentRepository.findByTeacherId(teacherId);
    }

    // Get students without teacher assigned
    public List<Student> getStudentsWithoutTeacher() {
        return studentRepository.findByTeacherIsNull();
    }

    // Search students by name
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    // Get students by teacher's subject
    public List<Student> getStudentsByTeacherSubject(String subject) {
        return studentRepository.findByTeacherSubject(subject);
    }

    // Get students enrolled between dates
    public List<Student> getStudentsEnrolledBetween(LocalDate startDate, LocalDate endDate) {
        return studentRepository.findByEnrollmentDateBetween(startDate, endDate);
    }

    // Update student
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (!student.getEmail().equals(studentDetails.getEmail()) &&
                studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new RuntimeException("Student with email " + studentDetails.getEmail() + " already exists");
        }

        // Check if student ID is being changed and if new student ID already exists
        if (studentDetails.getStudentId() != null &&
                !studentDetails.getStudentId().equals(student.getStudentId()) &&
                studentRepository.existsByStudentId(studentDetails.getStudentId())) {
            throw new RuntimeException("Student with ID " + studentDetails.getStudentId() + " already exists");
        }

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        student.setEnrollmentDate(studentDetails.getEnrollmentDate());
        student.setGradeLevel(studentDetails.getGradeLevel());
        student.setStudentId(studentDetails.getStudentId());

        return studentRepository.save(student);
    }

    // Assign teacher to student
    public Student assignTeacherToStudent(Long studentId, Long teacherId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

        student.setTeacher(teacher);
        return studentRepository.save(student);
    }

    // Remove teacher from student
    public Student removeTeacherFromStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        student.setTeacher(null);
        return studentRepository.save(student);
    }

    // Delete student
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        studentRepository.delete(student);
    }

    // Check if student exists
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    // Check if student exists by email
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    // Check if student exists by student ID
    public boolean existsByStudentId(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }

    // Get student count by grade level
    public long getStudentCountByGradeLevel(String gradeLevel) {
        return studentRepository.countByGradeLevel(gradeLevel);
    }

    // Get student count by teacher
    public long getStudentCountByTeacher(Long teacherId) {
        return studentRepository.countByTeacherId(teacherId);
    }
}
