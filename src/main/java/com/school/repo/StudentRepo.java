package com.school.repo;

import com.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    // Find student by email
    Optional<Student> findByEmail(String email);

    // Find student by student ID
    Optional<Student> findByStudentId(String studentId);

    // Find students by grade level
    List<Student> findByGradeLevel(String gradeLevel);

    // Find students by teacher ID
    List<Student> findByTeacherId(Long teacherId);

    // Find students by first name and last name
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);

    // Find students by first name or last name containing (case insensitive)
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    // Find students enrolled between dates
    List<Student> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);

    // Find students enrolled after a specific date
    List<Student> findByEnrollmentDateAfter(LocalDate date);

    // Find students without a teacher assigned
    List<Student> findByTeacherIsNull();

    // Custom query to find students with their teacher information
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.teacher")
    List<Student> findAllWithTeacher();

    // Custom query to find students by teacher's subject
    @Query("SELECT s FROM Student s JOIN s.teacher t WHERE t.subject = :subject")
    List<Student> findByTeacherSubject(@Param("subject") String subject);

    // Check if student exists by email
    boolean existsByEmail(String email);

    // Check if student exists by student ID
    boolean existsByStudentId(String studentId);

    // Count students by grade level
    @Query("SELECT COUNT(s) FROM Student s WHERE s.gradeLevel = :gradeLevel")
    long countByGradeLevel(@Param("gradeLevel") String gradeLevel);

    // Count students by teacher
    @Query("SELECT COUNT(s) FROM Student s WHERE s.teacher.id = :teacherId")
    long countByTeacherId(@Param("teacherId") Long teacherId);
}
