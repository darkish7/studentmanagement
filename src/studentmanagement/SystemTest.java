package studentmanagement;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class SystemTest {
    public static void main(String[] args) {
        System.out.println("Running Automated Tests...");

        // 1. Test InputValidator
        assert InputValidator.isValidMarks(85.0) : "Valid mark failed";
        assert !InputValidator.isValidMarks(105.0) : "Invalid mark passed";
        assert !InputValidator.isValidMarks(-5.0) : "Negative mark passed";
        assert InputValidator.isValidSemester(4) : "Valid sem failed";
        assert !InputValidator.isValidSemester(9) : "Invalid sem passed";
        assert InputValidator.isValidRegNo("REG101") : "Valid regNo failed";
        assert !InputValidator.isValidRegNo("!@#") : "Invalid regNo passed";
        assert InputValidator.isValidText("John Doe") : "Valid text failed";

        // 2. Test GradeCalculator
        assert "A+".equals(GradeCalculator.calculateGrade(95.0)) : "Grade 95 should be A+";
        assert "A".equals(GradeCalculator.calculateGrade(85.0)) : "Grade 85 should be A";
        assert "B".equals(GradeCalculator.calculateGrade(75.0)) : "Grade 75 should be B";
        assert "C".equals(GradeCalculator.calculateGrade(65.0)) : "Grade 65 should be C";
        assert "D".equals(GradeCalculator.calculateGrade(55.0)) : "Grade 55 should be D";
        assert "F".equals(GradeCalculator.calculateGrade(45.0)) : "Grade 45 should be F";

        // 3. Test Student model
        Student s = new Student("TEST01", "Test Student", "CSE", 3, 90.0, 80.0, 70.0);
        assert s.getTotal() == 240.0 : "Total marks mismatch";
        assert Math.abs(s.getAverage() - 80.0) < 0.001 : "Average calculation mismatch";
        assert "A".equals(s.getGrade()) : "Grade mismatch";
        assert s.isPassed() : "Student should pass";

        // Test CSV serialization
        String csvLine = s.toCsv();
        Student fromCsv = Student.fromCsv(csvLine);
        assert fromCsv != null && fromCsv.getRegNo().equals("TEST01") : "CSV deserialization failed";
        assert Math.abs(fromCsv.getMark1() - 90.0) < 0.001 : "CSV Mark1 mismatch";

        // 4. Test Repository & Service
        String testDb = "data/test_students.csv";
        new File(testDb).delete();

        StudentRepository repo = new CsvStudentRepository(testDb);
        StudentService service = new StudentService(repo);

        assert service.addStudent(s) : "Adding student failed";
        assert !service.addStudent(s) : "Adding duplicate student should fail";
        assert service.getTotalCount() == 1 : "Total count mismatch";

        Optional<Student> found = service.getStudentByRegNo("TEST01");
        assert found.isPresent() && found.get().getName().equals("Test Student") : "Find student failed";

        List<Student> searchRes = service.search("CSE");
        assert searchRes.size() == 1 : "Search failed";

        GradeCalculator.ClassStatistics stats = service.getClassStatistics();
        assert stats.totalStudents == 1 : "Stats total mismatch";
        assert stats.passedCount == 1 : "Stats passed mismatch";

        // Cleanup
        new File(testDb).delete();

        System.out.println("ALL 4 AUTOMATED TEST SUITES PASSED SUCCESSFULLY!");
    }
}
