package studentmanagement;

import java.util.List;
import java.util.Optional;

/**
 * Interface for student persistence and repository operations.
 */
public interface StudentRepository {
    void save(Student student);
    Optional<Student> findByRegNo(String regNo);
    List<Student> findAll();
    boolean update(Student student);
    boolean deleteByRegNo(String regNo);
    boolean existsByRegNo(String regNo);
    List<Student> searchByName(String name);
    List<Student> findByDepartment(String department);
}
