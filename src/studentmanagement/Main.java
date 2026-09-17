package studentmanagement;

/**
 * Main application entry point for the Student Management System.
 */
public class Main {

    private static final String DATA_FILE = "data/students.csv";

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     Initializing Student Management System...    ");
        System.out.println("=================================================");

        StudentRepository repository = new CsvStudentRepository(DATA_FILE);
        StudentService studentService = new StudentService(repository);

        // Seed demo data if storage is empty
        seedInitialDataIfEmpty(studentService);

        MenuHandler menuHandler = new MenuHandler(studentService);
        menuHandler.start();
    }

    private static void seedInitialDataIfEmpty(StudentService service) {
        if (service.getTotalCount() == 0) {
            System.out.println("[INFO] Seeding initial sample student records...");

            Student s1 = new Student("2024CS01", "Alice Johnson", "Computer Science", 4, 92.0, 88.5, 95.0);
            service.addStudent(s1);

            Student s2 = new Student("2024ME02", "Bob Smith", "Mechanical Eng", 4, 78.0, 82.0, 74.5);
            service.addStudent(s2);

            Student s3 = new Student("2024EE03", "Charlie Brown", "Electrical Eng", 4, 64.0, 58.0, 62.5);
            service.addStudent(s3);

            Student s4 = new Student("2024CS04", "Diana Prince", "Computer Science", 4, 96.0, 98.0, 94.5);
            service.addStudent(s4);

            System.out.println("[INFO] Sample records created.");
        }
    }
}
