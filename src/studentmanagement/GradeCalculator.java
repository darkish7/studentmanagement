package studentmanagement;

import java.util.*;

/**
 * Utility class for grade computation and statistical calculations.
 */
public class GradeCalculator {

    private GradeCalculator() {
        // Utility class
    }

    public static String calculateGrade(double average) {
        if (average >= 90)
            return "A+";
        if (average >= 80)
            return "A";
        if (average >= 70)
            return "B";
        if (average >= 60)
            return "C";
        if (average >= 50)
            return "D";
        return "F";
    }

    public static double calculateGpa(double average) {
        if (average >= 90.0) {
            return 4.0;
        } else if (average >= 80.0) {
            return 3.5 + ((average - 80.0) / 10.0) * 0.4;
        } else if (average >= 70.0) {
            return 3.0 + ((average - 70.0) / 10.0) * 0.4;
        } else if (average >= 60.0) {
            return 2.0 + ((average - 60.0) / 10.0) * 0.9;
        } else if (average >= 50.0) {
            return 1.0 + ((average - 50.0) / 10.0) * 0.9;
        } else {
            return 0.0;
        }
    }

    public static String getRemark(String grade) {
        if (grade == null) return "N/A";
        switch (grade.toUpperCase()) {
            case "A+": return "Outstanding Performance";
            case "A": return "Excellent Performance";
            case "B": return "Good / Above Average";
            case "C": return "Average";
            case "D": return "Pass / Needs Improvement";
            case "F": return "Fail / Unsatisfactory";
            default: return "N/A";
        }
    }

    public static class ClassStatistics {
        public int totalStudents;
        public int passedCount;
        public int failedCount;
        public double passRate;
        public double classAverage;
        public double highestAverage;
        public double lowestAverage;
        public Student topStudent;
        public Student lowestStudent;
        public Map<String, Integer> gradeDistribution = new LinkedHashMap<>();

        public ClassStatistics() {
            gradeDistribution.put("A+", 0);
            gradeDistribution.put("A", 0);
            gradeDistribution.put("B", 0);
            gradeDistribution.put("C", 0);
            gradeDistribution.put("D", 0);
            gradeDistribution.put("F", 0);
        }
    }

    public static ClassStatistics computeStatistics(List<Student> students) {
        ClassStatistics stats = new ClassStatistics();
        if (students == null || students.isEmpty()) {
            return stats;
        }

        stats.totalStudents = students.size();
        double sum = 0.0;
        stats.highestAverage = -1.0;
        stats.lowestAverage = 101.0;

        for (Student s : students) {
            double avg = s.getAverage();
            sum += avg;

            if (s.isPassed()) {
                stats.passedCount++;
            } else {
                stats.failedCount++;
            }

            if (avg > stats.highestAverage) {
                stats.highestAverage = avg;
                stats.topStudent = s;
            }

            if (avg < stats.lowestAverage) {
                stats.lowestAverage = avg;
                stats.lowestStudent = s;
            }

            String grade = s.getGrade();
            stats.gradeDistribution.put(grade, stats.gradeDistribution.getOrDefault(grade, 0) + 1);
        }

        stats.classAverage = sum / stats.totalStudents;
        stats.passRate = ((double) stats.passedCount / stats.totalStudents) * 100.0;
        if (stats.lowestAverage > 100.0) {
            stats.lowestAverage = 0.0;
        }

        return stats;
    }
}
