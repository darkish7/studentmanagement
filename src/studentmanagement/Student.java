package studentmanagement;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a student with registration number, personal details, semester, and subject marks.
 */
public class Student {
    private String regNo;
    private String name;
    private String department;
    private int semester;
    private double mark1;
    private double mark2;
    private double mark3;

    public Student(String regNo, String name, String department, int semester,
                   double mark1, double mark2, double mark3) {
        this.regNo = regNo != null ? regNo.trim() : "";
        this.name = name != null ? name.trim() : "";
        this.department = department != null ? department.trim() : "";
        this.semester = semester;
        this.mark1 = Math.max(0.0, Math.min(100.0, mark1));
        this.mark2 = Math.max(0.0, Math.min(100.0, mark2));
        this.mark3 = Math.max(0.0, Math.min(100.0, mark3));
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo != null ? regNo.trim() : "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department != null ? department.trim() : "";
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getMark1() {
        return mark1;
    }

    public void setMark1(double mark1) {
        this.mark1 = Math.max(0.0, Math.min(100.0, mark1));
    }

    public double getMark2() {
        return mark2;
    }

    public void setMark2(double mark2) {
        this.mark2 = Math.max(0.0, Math.min(100.0, mark2));
    }

    public double getMark3() {
        return mark3;
    }

    public void setMark3(double mark3) {
        this.mark3 = Math.max(0.0, Math.min(100.0, mark3));
    }

    public void setMarks(double mark1, double mark2, double mark3) {
        this.mark1 = Math.max(0.0, Math.min(100.0, mark1));
        this.mark2 = Math.max(0.0, Math.min(100.0, mark2));
        this.mark3 = Math.max(0.0, Math.min(100.0, mark3));
    }

    public double getTotal() {
        return mark1 + mark2 + mark3;
    }

    public double getAverage() {
        return getTotal() / 3.0;
    }

    public String getGrade() {
        return GradeCalculator.calculateGrade(getAverage());
    }

    public double getGpa() {
        return GradeCalculator.calculateGpa(getAverage());
    }

    public boolean isPassed() {
        return getAverage() >= 50.0 && mark1 >= 40.0 && mark2 >= 40.0 && mark3 >= 40.0;
    }

    public String toCsv() {
        return String.format(Locale.US, "%s,%s,%s,%d,%.2f,%.2f,%.2f",
                escapeCsv(regNo),
                escapeCsv(name),
                escapeCsv(department),
                semester,
                mark1,
                mark2,
                mark3);
    }

    public static Student fromCsv(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("(?<!\\\\),", -1);
        if (parts.length < 7) {
            return null;
        }
        try {
            String regNo = unescapeCsv(parts[0]);
            String name = unescapeCsv(parts[1]);
            String department = unescapeCsv(parts[2]);
            int semester = Integer.parseInt(parts[3].trim());
            double mark1 = Double.parseDouble(parts[4].trim());
            double mark2 = Double.parseDouble(parts[5].trim());
            double mark3 = Double.parseDouble(parts[6].trim());
            return new Student(regNo, name, department, semester, mark1, mark2, mark3);
        } catch (Exception e) {
            return null;
        }
    }

    private static String escapeCsv(String val) {
        if (val == null) return "";
        return val.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String unescapeCsv(String val) {
        if (val == null) return "";
        return val.replace("\\,", ",").replace("\\\\", "\\");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(regNo.toLowerCase(Locale.ROOT), student.regNo.toLowerCase(Locale.ROOT));
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Student[RegNo='%s', Name='%s', Dept='%s', Sem=%d, Avg=%.2f, Grade='%s']",
                regNo, name, department, semester, getAverage(), getGrade());
    }
}
