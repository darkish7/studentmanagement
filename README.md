# Student Management & Academic Performance Analytics System

A comprehensive, modular, and persistent command-line Student Management System built with Java using Object-Oriented Architecture and Design Principles.

---

## 📌 Project Overview
The **Student Management System** provides academic institutions and department faculty with a centralized tool to manage student profiles, record subject-level performance across semesters, compute GPAs and letter grades dynamically, analyze cohort statistics (e.g., class averages, pass rates, topper identification, and grade distributions), and persist data reliably using file-based CSV storage.

---

## ✨ Features

- **Student Profile Management (CRUD)**:
  - Add new students with auto-validated Registration Numbers, Names, Departments, and Semesters.
  - Record and modify marks across subjects (Mark 1, Mark 2, Mark 3).
  - Update student demographic details or marks with data validation.
  - Delete student records with confirmation prompts.

- **Academic Performance & Grading Engine**:
  - Automatic calculation of Total Score, Average Percentage, GPA (4.0 scale), and Letter Grades (`A+`, `A`, `B`, `C`, `D`, `F`).
  - Pass/Fail verification based on average score and minimum subject thresholds.

- **Cohort Analytics & Reporting**:
  - Formatted tabular student registry view with clean column alignments.
  - Individual Student Academic Report Cards.
  - Class-wide statistical summary including top/lowest performer identification and ASCII-based grade distribution histograms.
  - Dynamic multi-criteria sorting (by Name, Registration Number, or Average Marks).

- **Data Persistence & Integrity**:
  - File-based persistent storage (`data/students.csv`) with automatic loading and caching.
  - Robust input validation (regex checks, numeric bounds, error recovery, and EOF protection).
  - Summary report export to text file (`data/students_report.txt`).

---

## 🛠️ Technologies & Tools Used

- **Language**: Java (SE 17+ / JDK 21+ / JDK 26 compatible)
- **Paradigm**: Object-Oriented Programming (Encapsulation, Polymorphism, Abstraction, Separation of Concerns)
- **Data Persistence**: CSV File I/O (`java.nio.file`, `BufferedReader`/`BufferedWriter`)
- **Version Control**: Git & GitHub
- **Testing**: Automated Unit & Scenario Verification (`SystemTest.java`)

---

## 📂 Project Structure

```
studentmanagement/
├── data/
│   ├── students.csv             # Persistent student database
│   ├── students_report.txt      # Exported report files
│   ├── statement.md             # Detailed problem statement & scope
│   └── README.md                # Quick data reference
├── doc/
│   └── PROJECT_REPORT.md        # Comprehensive 15-section project report
├── src/
│   └── studentmanagement/
│       ├── Student.java             # Student data model & calculations
│       ├── StudentRepository.java   # Data access interface
│       ├── CsvStudentRepository.java# CSV file persistence implementation
│       ├── GradeCalculator.java     # Grading logic & cohort analytics
│       ├── InputValidator.java      # Safe console inputs & validation
│       ├── StudentService.java      # Core business logic layer
│       ├── ReportService.java       # ASCII tables, report cards & file exports
│       ├── MenuHandler.java         # Interactive CLI menu navigation
│       ├── Main.java                # Application bootstrap & sample seeding
│       └── SystemTest.java          # Automated test suite
├── README.md                    # Root project documentation
└── statement.md                 # Root problem statement & target users
```

---

## 🚀 Steps to Install & Run

### Prerequisites
- Java Development Kit (JDK 17 or higher) installed.
- Ensure `javac` and `java` are available in your environment path.

### 1. Clone the Repository
```bash
git clone https://github.com/{your-username}/studentmanagement.git
cd studentmanagement
```

### 2. Compile the Source Code
```bash
# Windows PowerShell / CMD
javac -d bin -sourcepath src src/studentmanagement/*.java

# Linux / macOS
javac -d bin -sourcepath src src/studentmanagement/*.java
```

### 3. Run the Application
```bash
# Windows / Linux / macOS
java -cp bin studentmanagement.Main
```

---

## 🧪 Instructions for Testing

An automated verification suite is included in `SystemTest.java` that tests input validation, grade calculations, student model methods, and repository persistence.

To execute the test suite:
```bash
java -ea -cp bin studentmanagement.SystemTest
```

**Expected Output**:
```
Running Comprehensive Automated Tests...
ALL AUTOMATED TEST SUITES PASSED SUCCESSFULLY!
```

---

## 🖥️ Sample Console Outputs & Screenshots

### 1. Main Navigation Menu
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

### 2. Student Registry Table
```text
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
| Reg No     | Name                 | Department         | Sem | Mark 1 | Mark 2 | Mark 3 | Avg (%) | Grade | Status |
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
| 2024CS01   | Alice Johnson        | Computer Science   | 4   | 92.0   | 88.5   | 95.0   | 91.8%   | A+    | PASS   |
| 2024ME02   | Bob Smith            | Mechanical Eng     | 4   | 78.0   | 82.0   | 74.5   | 78.2%   | B     | PASS   |
| 2024EE03   | Charlie Brown        | Electrical Eng     | 4   | 64.0   | 58.0   | 62.5   | 61.5%   | C     | PASS   |
| 2024CS04   | Diana Prince         | Computer Science   | 4   | 96.0   | 98.0   | 94.5   | 96.2%   | A+    | PASS   |
+------------+----------------------+--------------------+-----+--------+--------+--------+---------+-------+--------+
 Total Students: 4
```

### 3. Individual Report Card
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

### 4. Class Performance & Analytics
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
   Grade A+ [ 2 students] : ████████████
   Grade A  [ 0 students] : 
   Grade B  [ 1 students] : ██████
   Grade C  [ 1 students] : ██████
   Grade D  [ 0 students] : 
   Grade F  [ 0 students] : 
=======================================================
```

---

## 📄 Documentation & Project Report

For detailed academic documentation matching the course evaluation rubric, refer to:
- [**`statement.md`**](file:///c:/Users/bisen/OneDrive/Desktop/coding/studentmanagement/statement.md): Project statement, scope, target users, and functional specifications.
- [**`doc/PROJECT_REPORT.md`**](file:///c:/Users/bisen/OneDrive/Desktop/coding/studentmanagement/doc/PROJECT_REPORT.md): Comprehensive 15-section project report containing UML diagrams, architecture diagrams, sequence workflows, design decisions, and testing outcomes.
