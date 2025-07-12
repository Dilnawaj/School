package com.school.repo;

import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {

    // Find teacher by email
    Optional<Teacher> findByEmail(String email);

    // Find teachers by department
    List<Teacher> findByDepartment(String department);

    // Find teachers by subject
    List<Teacher> findBySubject(String subject);

    // Find teachers by first name and last name
    List<Teacher> findByFirstNameAndLastName(String firstName, String lastName);

    // Find teachers by first name or last name containing (case insensitive)
    List<Teacher> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    // Custom query to find teachers with students count
    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.students")
    List<Teacher> findAllWithStudents();

    // Custom query to find teachers by department with their students
    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.students WHERE t.department = :department")
    List<Teacher> findByDepartmentWithStudents(@Param("department") String department);

    // Check if teacher exists by email
    boolean existsByEmail(String email);

    // Count teachers by department
    @Query("SELECT COUNT(t) FROM Teacher t WHERE t.department = :department")
    long countByDepartment(@Param("department") String department);
}
