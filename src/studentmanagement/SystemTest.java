package studentmanagement;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SystemTest {
    public static void main(String[] args) {
        System.out.println("Running Comprehensive Automated Tests...");

        // 1. Test InputValidator
        assert InputValidator.isValidMarks(0.0) : "Boundary mark 0.0 failed";
        assert InputValidator.isValidMarks(100.0) : "Boundary mark 100.0 failed";
        assert InputValidator.isValidMarks(85.5) : "Valid mark 85.5 failed";
        assert !InputValidator.isValidMarks(100.01) : "Mark > 100 passed";
        assert !InputValidator.isValidMarks(-0.01) : "Negative mark passed";

        assert InputValidator.isValidSemester(1) : "Semester 1 failed";
        assert InputValidator.isValidSemester(8) : "Semester 8 failed";
        assert InputValidator.isValidSemester(4) : "Valid semester 4 failed";
        assert !InputValidator.isValidSemester(0) : "Semester 0 passed";
        assert !InputValidator.isValidSemester(9) : "Semester 9 passed";

        assert InputValidator.isValidRegNo("REG101") : "Valid regNo failed";
        assert InputValidator.isValidRegNo("2024_CS-01") : "Valid regNo with hyphen/underscore failed";
        assert !InputValidator.isValidRegNo("A") : "Too short regNo passed";
        assert !InputValidator.isValidRegNo("REGISTRATION_NUMBER_TOO_LONG_12345") : "Too long regNo passed";
        assert !InputValidator.isValidRegNo("!@#") : "Special character regNo passed";
        assert !InputValidator.isValidRegNo(null) : "Null regNo passed";

        assert InputValidator.isValidText("John Doe") : "Valid text failed";
        assert !InputValidator.isValidText("") : "Empty text passed";
        assert !InputValidator.isValidText("   ") : "Whitespace text passed";
        assert !InputValidator.isValidText(null) : "Null text passed";

        // 2. Test GradeCalculator
        assert "A+".equals(GradeCalculator.calculateGrade(100.0)) : "Grade 100 should be A+";
        assert "A+".equals(GradeCalculator.calculateGrade(90.0)) : "Grade 90 should be A+";
        assert "A".equals(GradeCalculator.calculateGrade(89.9)) : "Grade 89.9 should be A";
        assert "A".equals(GradeCalculator.calculateGrade(80.0)) : "Grade 80 should be A";
        assert "B".equals(GradeCalculator.calculateGrade(79.9)) : "Grade 79.9 should be B";
        assert "B".equals(GradeCalculator.calculateGrade(70.0)) : "Grade 70 should be B";
        assert "C".equals(GradeCalculator.calculateGrade(69.9)) : "Grade 69.9 should be C";
        assert "C".equals(GradeCalculator.calculateGrade(60.0)) : "Grade 60 should be C";
        assert "D".equals(GradeCalculator.calculateGrade(59.9)) : "Grade 59.9 should be D";
        assert "D".equals(GradeCalculator.calculateGrade(50.0)) : "Grade 50 should be D";
        assert "F".equals(GradeCalculator.calculateGrade(49.9)) : "Grade 49.9 should be F";
        assert "F".equals(GradeCalculator.calculateGrade(0.0)) : "Grade 0 should be F";

        assert GradeCalculator.calculateGpa(95.0) == 4.0 : "GPA for 95 should be 4.0";
        assert GradeCalculator.calculateGpa(45.0) == 0.0 : "GPA for 45 should be 0.0";
        assert GradeCalculator.getRemark("A+").contains("Outstanding") : "Remark for A+ incorrect";
        assert GradeCalculator.getRemark("F").contains("Fail") : "Remark for F incorrect";
        assert "N/A".equals(GradeCalculator.getRemark(null)) : "Null remark should be N/A";

        // 3. Test Student model & CSV escaping
        Student s = new Student("TEST01", "Test, Student", "Dept, CS", 3, 90.0, 80.0, 70.0);
        assert s.getTotal() == 240.0 : "Total marks mismatch";
        assert Math.abs(s.getAverage() - 80.0) < 0.001 : "Average calculation mismatch";
        assert "A".equals(s.getGrade()) : "Grade mismatch";
        assert s.isPassed() : "Student should pass";

        // Test mark clamping
        Student sClamped = new Student("CLAMP01", "Clamped", "IT", 1, 150.0, -20.0, 85.0);
        assert sClamped.getMark1() == 100.0 : "Mark1 should be clamped to 100";
        assert sClamped.getMark2() == 0.0 : "Mark2 should be clamped to 0";

        // Test CSV serialization with commas in name/dept
        String csvLine = s.toCsv();
        Student fromCsv = Student.fromCsv(csvLine);
        assert fromCsv != null : "CSV deserialization should not be null";
        assert fromCsv.getRegNo().equals("TEST01") : "CSV RegNo mismatch";
        assert fromCsv.getName().equals("Test, Student") : "CSV Name with comma deserialization failed";
        assert fromCsv.getDepartment().equals("Dept, CS") : "CSV Dept with comma deserialization failed";
        assert Math.abs(fromCsv.getMark1() - 90.0) < 0.001 : "CSV Mark1 mismatch";

        // Test invalid CSV deserialization
        assert Student.fromCsv(null) == null : "Null CSV line should return null";
        assert Student.fromCsv("") == null : "Empty CSV line should return null";
        assert Student.fromCsv("invalid,csv") == null : "Malformed CSV line should return null";
        assert Student.fromCsv("A,B,C,invalid_sem,10,20,30") == null : "Bad numeric sem should return null";

        // Test equals and hashCode case-insensitivity
        Student sCase1 = new Student("reg100", "Alice", "CSE", 2, 80, 80, 80);
        Student sCase2 = new Student("REG100", "Alice", "CSE", 2, 80, 80, 80);
        assert sCase1.equals(sCase2) : "Student equals should be case-insensitive on regNo";
        assert sCase1.hashCode() == sCase2.hashCode() : "Student hashCode should match for same regNo";

        // 4. Test Repository & Service
        String testDb = "data/test_students.csv";
        new File(testDb).delete();

        StudentRepository repo = new CsvStudentRepository(testDb);
        StudentService service = new StudentService(repo);

        assert service.addStudent(s) : "Adding student failed";
        assert !service.addStudent(s) : "Adding duplicate student should fail";
        assert service.getTotalCount() == 1 : "Total count mismatch";

        Optional<Student> found = service.getStudentByRegNo("test01"); // case-insensitive check
        assert found.isPresent() && found.get().getName().equals("Test, Student") : "Find student case-insensitive failed";

        List<Student> searchRes = service.search("dept");
        assert searchRes.size() == 1 : "Search failed";

        // Test sorting
        Student s2 = new Student("TEST02", "Alex", "ECE", 2, 95.0, 95.0, 95.0);
        service.addStudent(s2);
        List<Student> sortedByName = service.getStudentsSorted(Comparator.comparing(Student::getName));
        assert sortedByName.get(0).getName().equals("Alex") : "Sort by name failed";

        List<Student> sortedByAvgDesc = service.getStudentsSorted(Comparator.comparingDouble(Student::getAverage).reversed());
        assert sortedByAvgDesc.get(0).getRegNo().equals("TEST02") : "Sort by avg desc failed";

        // Test update
        s.setName("Updated Name");
        assert service.updateStudent(s) : "Update student failed";
        assert service.getStudentByRegNo("TEST01").get().getName().equals("Updated Name") : "Updated name not reflected";

        // Test Statistics
        GradeCalculator.ClassStatistics stats = service.getClassStatistics();
        assert stats.totalStudents == 2 : "Stats total mismatch";
        assert stats.passedCount == 2 : "Stats passed mismatch";
        assert stats.topStudent.getRegNo().equals("TEST02") : "Top student mismatch";

        // Test Persistence reload across instances
        StudentRepository reloadedRepo = new CsvStudentRepository(testDb);
        assert reloadedRepo.findAll().size() == 2 : "Persistence reload failed to restore records";
        assert reloadedRepo.findByRegNo("TEST01").isPresent() : "Reloaded record TEST01 missing";

        // Test delete
        assert service.deleteStudent("TEST01") : "Delete student failed";
        assert service.getTotalCount() == 1 : "Count after deletion mismatch";
        assert !service.exists("TEST01") : "Deleted student still exists";

        // 5. Test ReportService
        assert ReportService.exportReportToFile(service.getAllStudents(), "data/test_export.txt") : "Export report failed";
        assert new File("data/test_export.txt").exists() : "Export file was not created";

        // Cleanup test files
        new File(testDb).delete();
        new File("data/test_export.txt").delete();

        System.out.println("ALL AUTOMATED TEST SUITES PASSED SUCCESSFULLY!");
    }
}
