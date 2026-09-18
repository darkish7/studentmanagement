package studentmanagement;

import java.util.*;

/**
 * Console user interface handling menus, navigation, and user actions.
 */
public class MenuHandler {

    private final StudentService studentService;
    private final Scanner scanner;

    public MenuHandler(StudentService studentService) {
        this.studentService = studentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = InputValidator.readChoice(scanner, "Enter your choice (0-9): ", 0, 9);
            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleViewAllStudents();
                    break;
                case 3:
                    handleSearchStudent();
                    break;
                case 4:
                    handleUpdateStudent();
                    break;
                case 5:
                    handleDeleteStudent();
                    break;
                case 6:
                    handleViewReportCard();
                    break;
                case 7:
                    handleViewStatistics();
                    break;
                case 8:
                    handleSortStudents();
                    break;
                case 9:
                    handleExportReport();
                    break;
                case 0:
                    System.out.println("\nThank you for using Student Management System. Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=======================================================");
        System.out.println("            STUDENT MANAGEMENT SYSTEM v1.0             ");
        System.out.println("=======================================================");
        System.out.println("  [1] Add New Student");
        System.out.println("  [2] View All Students");
        System.out.println("  [3] Search Students (Reg No / Name / Department)");
        System.out.println("  [4] Update Student Details / Marks");
        System.out.println("  [5] Delete Student Record");
        System.out.println("  [6] View Individual Student Report Card");
        System.out.println("  [7] View Class Performance & Statistics");
        System.out.println("  [8] Sort Students (By Reg No, Name, or Average Score)");
        System.out.println("  [9] Export Student Summary Report to File");
        System.out.println("  [0] Exit Application");
        System.out.println("=======================================================");
    }

    private void handleAddStudent() {
        System.out.println("\n--- [ Add New Student ] ---");
        String regNo;
        while (true) {
            regNo = InputValidator.readRegNo(scanner, "Enter Registration Number (e.g. REG101): ");
            if (regNo.isEmpty()) {
                return;
            }
            if (studentService.exists(regNo)) {
                System.out.printf("(!) A student with Reg No '%s' already exists. Please choose a different number.%n", regNo);
            } else {
                break;
            }
        }

        String name = InputValidator.readText(scanner, "Enter Full Name: ");
        if (name.isEmpty()) {
            return;
        }

        String department = InputValidator.readText(scanner, "Enter Department (e.g. CSE, IT, MECH): ");
        if (department.isEmpty()) {
            return;
        }

        int semester = InputValidator.readSemester(scanner, "Enter Semester (1-8): ");

        System.out.println("Enter Subject Marks (0 - 100):");
        double m1 = InputValidator.readMarks(scanner, "  Mark 1: ");
        double m2 = InputValidator.readMarks(scanner, "  Mark 2: ");
        double m3 = InputValidator.readMarks(scanner, "  Mark 3: ");

        Student student = new Student(regNo, name, department, semester, m1, m2, m3);

        boolean added = studentService.addStudent(student);
        if (added) {
            System.out.printf("[SUCCESS] Student '%s' (Reg No: %s) added successfully!%n", student.getName(), student.getRegNo());
        } else {
            System.out.println("(!) Failed to add student.");
        }
    }

    private void handleViewAllStudents() {
        System.out.println("\n--- [ All Registered Students ] ---");
        List<Student> students = studentService.getAllStudents();
        ReportService.printStudentTable(students);
    }

    private void handleSearchStudent() {
        System.out.println("\n--- [ Search Students ] ---");
        String query = InputValidator.readText(scanner, "Enter Reg No, Name, or Department to search: ");
        if (query.isEmpty()) {
            return;
        }
        List<Student> results = studentService.search(query);
        System.out.printf("Found %d matching record(s):%n", results.size());
        ReportService.printStudentTable(results);
    }

    private void handleUpdateStudent() {
        System.out.println("\n--- [ Update Student ] ---");
        String regNo = InputValidator.readRegNo(scanner, "Enter Registration Number to update: ");
        if (regNo.isEmpty()) {
            return;
        }

        Optional<Student> opt = studentService.getStudentByRegNo(regNo);
        if (opt.isEmpty()) {
            System.out.printf("(!) Student with Reg No '%s' not found.%n", regNo);
            return;
        }

        Student student = opt.get();
        System.out.printf("Editing: %s (Department: %s, Sem: %d)%n", student.getName(), student.getDepartment(), student.getSemester());
        System.out.println("  1. Update Name");
        System.out.println("  2. Update Department");
        System.out.println("  3. Update Semester");
        System.out.println("  4. Update All Marks (Mark 1, Mark 2, Mark 3)");
        System.out.println("  0. Cancel");

        int choice = InputValidator.readChoice(scanner, "Select update option (0-4): ", 0, 4);
        switch (choice) {
            case 1:
                String newName = InputValidator.readText(scanner, "Enter new name: ");
                if (newName.isEmpty()) {
                    System.out.println("Update cancelled.");
                    return;
                }
                student.setName(newName);
                break;
            case 2:
                String newDept = InputValidator.readText(scanner, "Enter new department: ");
                if (newDept.isEmpty()) {
                    System.out.println("Update cancelled.");
                    return;
                }
                student.setDepartment(newDept);
                break;
            case 3:
                int newSem = InputValidator.readSemester(scanner, "Enter new semester (1-8): ");
                student.setSemester(newSem);
                break;
            case 4:
                double m1 = InputValidator.readMarks(scanner, "Enter Mark 1 (0-100): ");
                double m2 = InputValidator.readMarks(scanner, "Enter Mark 2 (0-100): ");
                double m3 = InputValidator.readMarks(scanner, "Enter Mark 3 (0-100): ");
                student.setMarks(m1, m2, m3);
                break;
            case 0:
                System.out.println("Update cancelled.");
                return;
            default:
                break;
        }

        if (studentService.updateStudent(student)) {
            System.out.println("[SUCCESS] Student record updated successfully.");
        } else {
            System.out.println("(!) Failed to update student record.");
        }
    }

    private void handleDeleteStudent() {
        System.out.println("\n--- [ Delete Student ] ---");
        String regNo = InputValidator.readRegNo(scanner, "Enter Registration Number to delete: ");
        if (regNo.isEmpty()) {
            return;
        }

        Optional<Student> opt = studentService.getStudentByRegNo(regNo);
        if (opt.isEmpty()) {
            System.out.printf("(!) Student with Reg No '%s' not found.%n", regNo);
            return;
        }

        Student student = opt.get();
        String confirm = InputValidator.readOptionalString(scanner,
                String.format("Are you sure you want to delete '%s' (Reg No: %s)? (y/n): ", student.getName(), student.getRegNo())).toLowerCase(Locale.ROOT);
        if (confirm.equals("y") || confirm.equals("yes")) {
            if (studentService.deleteStudent(regNo)) {
                System.out.println("[SUCCESS] Student deleted successfully.");
            } else {
                System.out.println("(!) Failed to delete student.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void handleViewReportCard() {
        System.out.println("\n--- [ View Student Report Card ] ---");
        String regNo = InputValidator.readRegNo(scanner, "Enter Registration Number: ");
        if (regNo.isEmpty()) {
            return;
        }

        Optional<Student> opt = studentService.getStudentByRegNo(regNo);
        if (opt.isEmpty()) {
            System.out.printf("(!) Student with Reg No '%s' not found.%n", regNo);
            return;
        }
        ReportService.printStudentReportCard(opt.get());
    }

    private void handleViewStatistics() {
        GradeCalculator.ClassStatistics stats = studentService.getClassStatistics();
        ReportService.printClassStatistics(stats);
    }

    private void handleSortStudents() {
        System.out.println("\n--- [ Sort Students ] ---");
        System.out.println("  1. Sort by Name (A-Z)");
        System.out.println("  2. Sort by Reg No (Ascending)");
        System.out.println("  3. Sort by Average Marks (Highest to Lowest)");
        System.out.println("  4. Sort by Average Marks (Lowest to Highest)");
        System.out.println("  0. Back to Main Menu");

        int sortChoice = InputValidator.readChoice(scanner, "Select sorting criterion (0-4): ", 0, 4);
        List<Student> sortedList;
        switch (sortChoice) {
            case 1:
                sortedList = studentService.getStudentsSorted(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            case 2:
                sortedList = studentService.getStudentsSorted(Comparator.comparing(Student::getRegNo, String.CASE_INSENSITIVE_ORDER));
                break;
            case 3:
                sortedList = studentService.getStudentsSorted(Comparator.comparingDouble(Student::getAverage).reversed());
                break;
            case 4:
                sortedList = studentService.getStudentsSorted(Comparator.comparingDouble(Student::getAverage));
                break;
            default:
                return;
        }
        ReportService.printStudentTable(sortedList);
    }

    private void handleExportReport() {
        System.out.println("\n--- [ Export Summary Report ] ---");
        String defaultPath = "data/students_report.txt";
        String inputPath = InputValidator.readOptionalString(scanner,
                String.format("Enter file path to export [Press Enter for '%s']: ", defaultPath));
        String path = inputPath.isEmpty() ? defaultPath : inputPath;

        boolean success = ReportService.exportReportToFile(studentService.getAllStudents(), path);
        if (success) {
            System.out.printf("[SUCCESS] Summary report exported to: %s%n", path);
        } else {
            System.out.println("(!) Failed to export report.");
        }
    }
}
