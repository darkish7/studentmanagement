# Student Management & Academic Performance Analytics System

A modular, persistent command-line Student Management System built in Java using object-oriented design principles.

---

## Project Overview

This system gives academic departments a single tool to manage student profiles, record subject-level performance across semesters, calculate GPA and letter grades, analyze cohort statistics (class averages, pass rates, topper identification, grade distributions), and store everything reliably using CSV-based file persistence.

---

## Features

- **Student Profile Management (CRUD)**
  - Add new students with validated registration numbers, names, departments, and semesters.
  - Record and update marks across three subjects.
  - Edit demographic details or marks with input validation.
  - Delete records with a confirmation prompt.

- **Academic Performance & Grading Engine**
  - Automatic calculation of total score, average percentage, GPA (4.0 scale), and letter grades (A+, A, B, C, D, F).
  - Pass/fail determination based on average score and minimum subject thresholds.

- **Cohort Analytics & Reporting**
  - Formatted, aligned student registry table.
  - Individual student report cards.
  - Class-wide statistics, including top/lowest performer identification and ASCII grade-distribution histograms.
  - Sorting by name, registration number, or average marks.

- **Data Persistence & Integrity**
  - File-based storage (`data/students.csv`) with automatic loading and caching.
  - Input validation with regex checks, numeric bounds, and error recovery.
  - Summary report export to a text file (`data/students_report.txt`).

---

## Technologies Used

- **Language:** Java (SE 17+, tested up to JDK 21/26)
- **Paradigm:** Object-oriented programming (encapsulation, polymorphism, abstraction, separation of concerns)
- **Persistence:** CSV file I/O (`java.nio.file`, `BufferedReader`/`BufferedWriter`)
- **Version control:** Git & GitHub
- **Testing:** Custom automated test suite (`SystemTest.java`)

---

## Project Structure

```
studentmanagement/
├── data/
│   ├── students.csv             # Persistent student database
│   ├── students_report.txt      # Exported report files
├── doc/
│   └── PROJECT_REPORT.md        # Full project report
├── src/
│   └── studentmanagement/
│       ├── Student.java             # Student data model & calculations
│       ├── StudentRepository.java   # Data access interface
│       ├── CsvStudentRepository.java# CSV file persistence implementation
│       ├── GradeCalculator.java     # Grading logic & cohort analytics
│       ├── InputValidator.java      # Console input validation
│       ├── StudentService.java      # Core business logic layer
│       ├── ReportService.java       # ASCII tables, report cards, file export
│       ├── MenuHandler.java         # Interactive CLI menu navigation
│       ├── Main.java                # Application entry point & sample data
│       └── SystemTest.java          # Automated test suite
├── README.md                    # Project documentation
└── statement.md                 # Problem statement & target users
```

---

## Installation & Setup

### Prerequisites
- JDK 17 or higher installed.
- `javac` and `java` available on your system path.

### 1. Clone the repository
```bash
git clone https://github.com/{your-username}/studentmanagement.git
cd studentmanagement
```

### 2. Compile the source code
```bash
javac -d bin -sourcepath src src/studentmanagement/*.java
```

### 3. Run the application
```bash
java -cp bin studentmanagement.Main
```

---

## Testing

An automated test suite in `SystemTest.java` covers input validation, grade calculations, model behavior, and repository persistence.

Run it with:
```bash
java -ea -cp bin studentmanagement.SystemTest
```

Expected output:
```
Running Comprehensive Automated Tests...
ALL AUTOMATED TEST SUITES PASSED SUCCESSFULLY!
```

---

## Sample Console Output

### Main menu
```text
=======================================================
            STUDENT MANAGEMENT SYSTEM v1.0
=======================================================
  [1] Add New Student
  [2] View All Students
  [3] Search Students (Reg No / Name / Department)
  [4] Update Student Details / Marks
  [5] Delete Student Record
  [6] View Individual Student Report Card
  [7] View Class Performance & Statistics
  [8] Sort Students (By Reg No, Name, or Average Score)
  [9] Export Student Summary Report to File
  [0] Exit Application
=======================================================
```

### Student registry table
```text
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
| Reg No     | Name                 | Department         | Sem | Mark 1 | Mark 2 | Mark 3 | Avg (%) | Grade | Status |
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
| 2024CS01   | Alice Johnson        | Computer Science   | 4   | 92.0   | 88.5   | 95.0   | 91.8%   | A+    | PASS   |
| 2024ME02   | Bob Smith            | Mechanical Eng      | 4   | 78.0   | 82.0   | 74.5   | 78.2%   | B     | PASS   |
| 2024EE03   | Charlie Brown        | Electrical Eng      | 4   | 64.0   | 58.0   | 62.5   | 61.5%   | C     | PASS   |
| 2024CS04   | Diana Prince         | Computer Science   | 4   | 96.0   | 98.0   | 94.5   | 96.2%   | A+    | PASS   |
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
 Total Students: 4
```

### Individual report card
```text
=======================================================
                 STUDENT REPORT CARD
=======================================================
  Registration No : 2024CS01
  Full Name       : Alice Johnson
  Department      : Computer Science
  Semester        : 4
-------------------------------------------------------
  Subject / Component       Score        Grade
  ---------------------------------------------------
  Subject 1                 92.00        A+
  Subject 2                 88.50        A
  Subject 3                 95.00        A+
-------------------------------------------------------
  Total Marks     : 275.50 / 300.00
  Percentage      : 91.83%
  Overall Grade   : A+ (Outstanding Performance)
  GPA (4.0 Scale) : 4.00 / 4.00
  Academic Result : [ PASSED ]
=======================================================
```

### Class performance & analytics
```text
=======================================================
             CLASS PERFORMANCE & STATISTICS
=======================================================
  Total Students Enrolled : 4
  Passed Students         : 4
  Failed Students         : 0
  Overall Pass Rate       : 100.00%
  Class Average Score     : 81.92%
  Top Performer           : Diana Prince (Reg: 2024CS04) - 96.17%
  Lowest Performer        : Charlie Brown (Reg: 2024EE03) - 61.50%
-------------------------------------------------------
  GRADE DISTRIBUTION:
   Grade A+ [ 2 students] : ############
   Grade A  [ 0 students] :
   Grade B  [ 1 students] : ######
   Grade C  [ 1 students] : ######
   Grade D  [ 0 students] :
   Grade F  [ 0 students] :
=======================================================
```

---

## Documentation

For more detailed documentation matching the course rubric, see:
- [`statement.md`](file:///c:/Users/bisen/OneDrive/Desktop/coding/studentmanagement/statement.md) — project statement, scope, target users, and functional specifications.
- [`doc/PROJECT_REPORT.md`](file:///c:/Users/bisen/OneDrive/Desktop/coding/studentmanagement/doc/PROJECT_REPORT.md) — full project report with UML diagrams, architecture diagrams, sequence workflows, design decisions, and testing outcomes.
