package studentmanagement;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Business logic service layer for Student Management.
 */
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository cannot be null");
    }

    public boolean addStudent(Student student) {
        if (student == null || student.getRegNo().isEmpty()) {
            return false;
        }
        if (repository.existsByRegNo(student.getRegNo())) {
            return false; // Student with this RegNo already exists
        }
        repository.save(student);
        return true;
    }

    public Optional<Student> getStudentByRegNo(String regNo) {
        return repository.findByRegNo(regNo);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public boolean updateStudent(Student student) {
        if (student == null) {
            return false;
        }
        return repository.update(student);
    }

    public boolean deleteStudent(String regNo) {
        return repository.deleteByRegNo(regNo);
    }

    public boolean exists(String regNo) {
        return repository.existsByRegNo(regNo);
    }

    public List<Student> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllStudents();
        }
        String q = query.trim().toLowerCase();
        return repository.findAll().stream()
                .filter(s -> s.getRegNo().toLowerCase().contains(q)
                        || s.getName().toLowerCase().contains(q)
                        || s.getDepartment().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsSorted(Comparator<Student> comparator) {
        return repository.findAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public GradeCalculator.ClassStatistics getClassStatistics() {
        return GradeCalculator.computeStatistics(repository.findAll());
    }

    public int getTotalCount() {
        return repository.findAll().size();
    }
}
