package studentmanagement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Service for formatted student reports, tables, report cards, and text file export.
 */
public class ReportService {

    public static void printStudentTable(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("\n+-----------------------------------------------------------------------------------------------+");
            System.out.println("|                                    NO STUDENTS FOUND                                          |");
            System.out.println("+-----------------------------------------------------------------------------------------------+");
            return;
        }

        String border = "+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+";
        System.out.println("\n" + border);
        System.out.printf(Locale.US, "| %-10s | %-20s | %-18s | %-3s | %-6s | %-6s | %-6s | %-7s | %-5s | %-6s |%n",
                "Reg No", "Name", "Department", "Sem", "Mark 1", "Mark 2", "Mark 3", "Avg (%)", "Grade", "Status");
        System.out.println(border);

        for (Student s : students) {
            String status = s.isPassed() ? "PASS" : "FAIL";
            System.out.printf(Locale.US, "| %-10s | %-20s | %-18s | %-3d | %-6.1f | %-6.1f | %-6.1f | %-7.1f | %-5s | %-6s |%n",
                    truncate(s.getRegNo(), 10),
                    truncate(s.getName(), 20),
                    truncate(s.getDepartment(), 18),
                    s.getSemester(),
                    s.getMark1(),
                    s.getMark2(),
                    s.getMark3(),
                    s.getAverage(),
                    s.getGrade(),
                    status);
        }
        System.out.println(border);
        System.out.printf(Locale.US, " Total Students: %d%n%n", students.size());
    }

    public static void printStudentReportCard(Student student) {
        if (student == null) {
            System.out.println("(!) Student record not found.");
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("                 STUDENT REPORT CARD                   ");
        System.out.println("=======================================================");
        System.out.printf("  Registration No : %s%n", student.getRegNo());
        System.out.printf("  Full Name       : %s%n", student.getName());
        System.out.printf("  Department      : %s%n", student.getDepartment());
        System.out.printf("  Semester        : %d%n", student.getSemester());
        System.out.println("-------------------------------------------------------");
        System.out.printf("  %-25s %-12s %-10s%n", "Subject / Component", "Score", "Grade");
        System.out.println("  ---------------------------------------------------");
        System.out.printf(Locale.US, "  %-25s %-12.2f %-10s%n", "Subject 1", student.getMark1(), GradeCalculator.calculateGrade(student.getMark1()));
        System.out.printf(Locale.US, "  %-25s %-12.2f %-10s%n", "Subject 2", student.getMark2(), GradeCalculator.calculateGrade(student.getMark2()));
        System.out.printf(Locale.US, "  %-25s %-12.2f %-10s%n", "Subject 3", student.getMark3(), GradeCalculator.calculateGrade(student.getMark3()));
        System.out.println("-------------------------------------------------------");
        System.out.printf(Locale.US, "  Total Marks     : %.2f / 300.00%n", student.getTotal());
        System.out.printf(Locale.US, "  Percentage      : %.2f%%%n", student.getAverage());
        System.out.printf("  Overall Grade   : %s (%s)%n", student.getGrade(), GradeCalculator.getRemark(student.getGrade()));
        System.out.printf(Locale.US, "  GPA (4.0 Scale) : %.2f / 4.00%n", student.getGpa());
        System.out.printf("  Academic Result : %s%n", student.isPassed() ? "[ PASSED ]" : "[ FAILED ]");
        System.out.println("=======================================================\n");
    }

    public static void printClassStatistics(GradeCalculator.ClassStatistics stats) {
        if (stats == null) {
            System.out.println("(!) No statistics available.");
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("             CLASS PERFORMANCE & STATISTICS            ");
        System.out.println("=======================================================");
        System.out.printf(Locale.US, "  Total Students Enrolled : %d%n", stats.totalStudents);
        System.out.printf(Locale.US, "  Passed Students         : %d%n", stats.passedCount);
        System.out.printf(Locale.US, "  Failed Students         : %d%n", stats.failedCount);
        System.out.printf(Locale.US, "  Overall Pass Rate       : %.2f%%%n", stats.passRate);
        System.out.printf(Locale.US, "  Class Average Score     : %.2f%%%n", stats.classAverage);

        if (stats.topStudent != null) {
            System.out.printf(Locale.US, "  Top Performer           : %s (Reg: %s) - %.2f%%%n",
                    stats.topStudent.getName(), stats.topStudent.getRegNo(), stats.topStudent.getAverage());
        }
        if (stats.lowestStudent != null) {
            System.out.printf(Locale.US, "  Lowest Performer        : %s (Reg: %s) - %.2f%%%n",
                    stats.lowestStudent.getName(), stats.lowestStudent.getRegNo(), stats.lowestStudent.getAverage());
        }

        System.out.println("-------------------------------------------------------");
        System.out.println("  GRADE DISTRIBUTION:");
        for (Map.Entry<String, Integer> entry : stats.gradeDistribution.entrySet()) {
            int count = entry.getValue();
            int barLength = stats.totalStudents > 0 ? (count * 25 / stats.totalStudents) : 0;
            String bar = "█".repeat(Math.max(0, barLength));
            System.out.printf(Locale.US, "   Grade %-2s [%2d students] : %s%n", entry.getKey(), count, bar);
        }
        System.out.println("=======================================================\n");
    }

    public static boolean exportReportToFile(List<Student> students, String filePathStr) {
        if (filePathStr == null || filePathStr.trim().isEmpty()) {
            return false;
        }
        List<Student> list = students != null ? students : Collections.emptyList();
        try {
            Path path = Paths.get(filePathStr);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write("STUDENT MANAGEMENT SYSTEM - SUMMARY REPORT\n");
                writer.write("Generated at: " + new Date() + "\n\n");
                writer.write(String.format(Locale.US, "%-10s %-20s %-18s %-5s %-7s %-7s %-7s %-8s %-6s %-6s\n",
                        "RegNo", "Name", "Department", "Sem", "Mark1", "Mark2", "Mark3", "Avg(%)", "Grade", "Status"));
                writer.write("-".repeat(105) + "\n");
                for (Student s : list) {
                    writer.write(String.format(Locale.US, "%-10s %-20s %-18s %-5d %-7.1f %-7.1f %-7.1f %-8.2f %-6s %-6s\n",
                            s.getRegNo(), s.getName(), s.getDepartment(), s.getSemester(),
                            s.getMark1(), s.getMark2(), s.getMark3(),
                            s.getAverage(), s.getGrade(), s.isPassed() ? "PASS" : "FAIL"));
                }
                writer.write("-".repeat(105) + "\n");
                writer.write("Total Students: " + list.size() + "\n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
            return false;
        }
    }

    private static String truncate(String val, int maxLen) {
        if (val == null) {
            return "";
        }
        if (val.length() <= maxLen) {
            return val;
        }
        return val.substring(0, Math.max(0, maxLen - 2)) + "..";
    }
}
