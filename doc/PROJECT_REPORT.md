# Comprehensive Project Report: Student Management & Academic Analytics System

---

## 1. Cover Page

- **Project Title**: Student Management and Academic Performance Analytics System
- **Course Name**: Programming in Java
- **Course Code**: CSE1007 / JAVA Evaluation Project
- **Domain**: Academic Information Systems & Educational Analytics
- **Platform / Framework**: Java Standard Edition (SE 17+)
- **Submission Date**: September 18, 2026
- **Submission Type**: VITyarthi - Build Your Own Project (Flipped Course Evaluation)

---

## 2. Introduction

The **Student Management and Academic Performance Analytics System** is a modular, object-oriented console application developed in Java. It addresses fundamental operational challenges in educational institutions by offering automated record management, dynamic GPA/grade computation, statistical class analytics, search and sorting facilities, and persistent CSV storage.

Built strictly around core Object-Oriented Programming (OOP) paradigms — including Encapsulation, Abstraction, Polymorphism, and Layered Architecture — this project demonstrates the practical application of the Java Collections Framework, Java NIO File I/O, Regular Expressions, and automated test-driven validation.

---

## 3. Problem Statement

In academic institutions and university departments, managing student performance across semesters often involves error-prone manual spreadsheets. This leads to several distinct problems:
1. **Human Error**: Inconsistent manual calculation of student averages, GPAs, and pass/fail statuses.
2. **Lack of Instant Analytics**: Instructors cannot quickly visualize grade distributions, identify students needing academic intervention, or find top performers.
3. **Data Loss & Inconsistency**: Lack of robust validation allows negative marks or malformed registration numbers, and session-only memory leads to data loss when the application terminates.
4. **Poor Extensibility**: Tightly coupled legacy codebases make adding new persistence models or reporting formats difficult.

This system provides a reliable, self-contained, and command-line accessible Java solution that completely resolves these challenges.

---

## 4. Functional Requirements

The system provides three major functional modules:

### 4.1 Module 1: Student Record & CRUD Management
- **FR-1.1**: Add student records with unique Registration Number, Full Name, Department, Semester (1–8), and subject marks.
- **FR-1.2**: View all registered students in a formatted tabular layout.
- **FR-1.3**: Search student records by Registration Number, Name substring, or Department.
- **FR-1.4**: Update student demographics or subject marks with real-time recalculation of total, average, and grade.
- **FR-1.5**: Delete existing records with confirmation to prevent accidental loss.

### 4.2 Module 2: Academic Grading & Analytics Engine
- **FR-2.1**: Calculate individual student totals, percentages, GPA (4.0 scale), and standard letter grades (`A+`, `A`, `B`, `C`, `D`, `F`).
- **FR-2.2**: Evaluate pass/fail status based on cumulative percentage ($\ge 50\%$) and minimum subject passing thresholds ($\ge 40$).
- **FR-2.3**: Generate individual academic Report Cards complete with subject breakdown and descriptive remarks.
- **FR-2.4**: Compute cohort-level metrics: overall pass rate, class average score, top scorer, lowest scorer, and ASCII-based grade distribution histograms.

### 4.3 Module 3: Sorting, Reporting & Persistence
- **FR-3.1**: Multi-criteria sorting (by Full Name, Registration Number, or Average Score ascending/descending).
- **FR-3.2**: Export formatted summary reports to external text files (`data/students_report.txt`).
- **FR-3.3**: Automatic CSV persistence (`data/students.csv`) synchronizing in-memory state with disk storage.

---

## 5. Non-Functional Requirements

1. **Performance**: In-memory caching using `LinkedHashMap` guarantees $O(1)$ lookup, insertion, and update operations for instant responsiveness.
2. **Reliability & Data Integrity**: Regex-based input validation rejects malformed registration numbers, invalid marks ($<0$ or $>100$), and corrupt inputs. Safe stream reading ensures error-free execution even under sudden EOF or stream terminations.
3. **Maintainability**: Layered separation of concerns (Model, Repository, Service, View/Menu, Utilities) allows components to be modified independently.
4. **Usability**: Clean terminal interface with clear visual hierarchy, aligned ASCII tables, informative prompts, and unambiguous error feedback.
5. **Resource Efficiency**: Minimal memory footprint without external heavy frameworks; executes smoothly on standard JVM environments.

---

## 6. System Architecture

The application adopts a **3-Tier Layered Architecture** with strict Separation of Concerns:

```
+-------------------------------------------------------------+
|                 PRESENTATION LAYER (CLI)                    |
|   MenuHandler, ReportService (Tables, Report Cards, Charts) |
+------------------------------+------------------------------+
                               |
+------------------------------v------------------------------+
|                   BUSINESS LOGIC LAYER                      |
|       StudentService, GradeCalculator, InputValidator       |
+------------------------------+------------------------------+
                               |
+------------------------------v------------------------------+
|                    DATA ACCESS LAYER                        |
|        StudentRepository (Interface) <--- CsvStudentRepo     |
+------------------------------+------------------------------+
                               |
+------------------------------v------------------------------+
|                    PERSISTENCE LAYER                        |
|                  data/students.csv (File)                   |
+-------------------------------------------------------------+
```

---

## 7. Design Diagrams

### 7.1 Use Case Diagram

```mermaid
usecaseDiagram
    actor Faculty as "Faculty / Administrator"
    
    package "Student Management System" {
        usecase UC1 as "Add New Student"
        usecase UC2 as "View Student Registry"
        usecase UC3 as "Search Student Records"
        usecase UC4 as "Update Profile & Marks"
        usecase UC5 as "Delete Student"
        usecase UC6 as "Generate Report Card"
        usecase UC7 as "View Class Analytics & Histogram"
        usecase UC8 as "Sort Student Records"
        usecase UC9 as "Export Summary Report"
        usecase UC10 as "Auto-Sync to CSV Disk"
    }

    Faculty --> UC1
    Faculty --> UC2
    Faculty --> UC3
    Faculty --> UC4
    Faculty --> UC5
    Faculty --> UC6
    Faculty --> UC7
    Faculty --> UC8
    Faculty --> UC9
    
    UC1 ..> UC10 : <<include>>
    UC4 ..> UC10 : <<include>>
    UC5 ..> UC10 : <<include>>
```

### 7.2 Process Flow / Workflow Diagram

```mermaid
flowchart TD
    Start([Launch Application]) --> LoadData[Load Students from CSV to Cache]
    LoadData --> CheckEmpty{Cache Empty?}
    CheckEmpty -- Yes --> SeedDemo[Seed Sample Data & Save to CSV]
    CheckEmpty -- No --> ShowMenu[Display Main Menu]
    SeedDemo --> ShowMenu
    
    ShowMenu --> ReadChoice[/User Enters Choice 0-9/]
    
    ReadChoice --> C1{Choice}
    C1 -- 1: Add --> InAdd[Validate RegNo, Name, Dept, Sem, Marks] --> SaveAdd[Save to Memory & CSV] --> ShowMenu
    C1 -- 2: View --> DisplayTable[Render ASCII Table] --> ShowMenu
    C1 -- 3: Search --> ExecSearch[Filter by Keyword] --> DisplayTable
    C1 -- 4: Update --> SelectRec[Find Student] --> ModFields[Update Fields] --> SaveUpdate[Save to CSV] --> ShowMenu
    C1 -- 5: Delete --> ConfirmDel{Confirm Deletion?} -- Yes --> DelRec[Remove from CSV] --> ShowMenu
    ConfirmDel -- No --> ShowMenu
    C1 -- 6: Report Card --> GenCard[Format & Display Report Card] --> ShowMenu
    C1 -- 7: Analytics --> CalcStats[Compute Averages & Histogram] --> ShowMenu
    C1 -- 8: Sort --> ApplySort[Apply Comparator] --> DisplayTable
    C1 -- 9: Export --> WriteFile[Write to students_report.txt] --> ShowMenu
    C1 -- 0: Exit --> Terminate([Exit Application])
```

### 7.3 Sequence Diagram: Adding a Student Record

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant MenuHandler
    participant InputValidator
    participant StudentService
    participant StudentRepository
    participant FileSystem as "CSV File (Disk)"

    User->>MenuHandler: Select Option 1 (Add Student)
    MenuHandler->>InputValidator: readRegNo(scanner)
    InputValidator-->>MenuHandler: Validated RegNo
    MenuHandler->>StudentService: exists(regNo)
    StudentService->>StudentRepository: existsByRegNo(regNo)
    StudentRepository-->>StudentService: false
    StudentService-->>MenuHandler: false
    
    MenuHandler->>InputValidator: readText(), readSemester(), readMarks()
    InputValidator-->>MenuHandler: name, dept, sem, marks
    
    MenuHandler->>StudentService: addStudent(Student)
    StudentService->>StudentRepository: save(Student)
    StudentRepository->>StudentRepository: update in-memory Map
    StudentRepository->>FileSystem: writeToCsv()
    FileSystem-->>StudentRepository: write success
    StudentRepository-->>StudentService: void
    StudentService-->>MenuHandler: true
    MenuHandler-->>User: Display Success Confirmation
```

### 7.4 Class / Component Diagram

```mermaid
classDiagram
    class Student {
        -String regNo
        -String name
        -String department
        -int semester
        -double mark1
        -double mark2
        -double mark3
        +getRegNo() String
        +getTotal() double
        +getAverage() double
        +getGrade() String
        +getGpa() double
        +isPassed() boolean
        +toCsv() String
        +fromCsv(String) Student
    }

    class StudentRepository {
        <<interface>>
        +save(Student) void
        +findByRegNo(String) Optional~Student~
        +findAll() List~Student~
        +update(Student) boolean
        +deleteByRegNo(String) boolean
        +existsByRegNo(String) boolean
        +searchByName(String) List~Student~
        +findByDepartment(String) List~Student~
    }

    class CsvStudentRepository {
        -Path filePath
        -Map~String, Student~ cache
        +save(Student) void
        +findByRegNo(String) Optional~Student~
        +findAll() List~Student~
        +update(Student) boolean
        +deleteByRegNo(String) boolean
        -loadFromCsv() void
        -saveToCsv() void
    }

    class StudentService {
        -StudentRepository repository
        +addStudent(Student) boolean
        +getStudentByRegNo(String) Optional~Student~
        +getAllStudents() List~Student~
        +updateStudent(Student) boolean
        +deleteStudent(String) boolean
        +search(String) List~Student~
        +getStudentsSorted(Comparator) List~Student~
        +getClassStatistics() ClassStatistics
    }

    class GradeCalculator {
        +calculateGrade(double) String
        +calculateGpa(double) double
        +getRemark(String) String
        +computeStatistics(List~Student~) ClassStatistics
    }

    class InputValidator {
        +isValidMarks(double) boolean
        +isValidSemester(int) boolean
        +isValidText(String) boolean
        +isValidRegNo(String) boolean
        +readRegNo(Scanner, String) String
        +readMarks(Scanner, String) double
        +readSemester(Scanner, String) int
    }

    class ReportService {
        +printStudentTable(List~Student~) void
        +printStudentReportCard(Student) void
        +printClassStatistics(ClassStatistics) void
        +exportReportToFile(List~Student~, String) boolean
    }

    class MenuHandler {
        -StudentService studentService
        -Scanner scanner
        +start() void
    }

    class Main {
        +main(String[]) void
    }

    StudentRepository <|.. CsvStudentRepository
    StudentService o-- StudentRepository
    MenuHandler o-- StudentService
    Main ..> MenuHandler
    Main ..> StudentService
    CsvStudentRepository ..> Student
    ReportService ..> Student
    ReportService ..> GradeCalculator
```

### 7.5 ER / Storage Schema Diagram

```mermaid
erDiagram
    STUDENT_CSV_RECORD {
        string regNo PK "Primary Key (Alphanumeric)"
        string name "Student Full Name"
        string department "Academic Major / Department"
        int semester "Semester Number (1 to 8)"
        double mark1 "Subject 1 Score (0-100)"
        double mark2 "Subject 2 Score (0-100)"
        double mark3 "Subject 3 Score (0-100)"
    }
```

---

## 8. Design Decisions & Rationale

1. **Repository Pattern with Interface**: Decoupling the data layer through `StudentRepository` interface allows switching storage mechanisms (e.g., SQLite, PostgreSQL, JSON) without altering any business logic.
2. **In-Memory Cache with Disk Sync**: Operations execute in $O(1)$ memory time, while file synchronization on every mutating operation prevents data loss during unexpected crashes.
3. **Static Utility Separation**: `GradeCalculator` and `InputValidator` are implemented as stateless utility classes with private constructors, keeping domain logic clean and highly testable.
4. **Defensive Programming & EOF Safety**: `InputValidator` checks stream boundaries to prevent `NoSuchElementException` when executed across varying terminal configurations.

---

## 9. Implementation Details

- **Package**: `studentmanagement`
- **Class Count**: 9 Core Production Classes + 1 Automated Test Suite.
- **Data Encapsulation**: All fields in `Student` are private with controlled accessors and mutators that enforce boundary constraints ($0 \le \text{mark} \le 100$).
- **File I/O**: Implemented using modern Java NIO (`Files.newBufferedReader`, `Files.newBufferedWriter`) with UTF-8 encoding.

---

## 10. Screenshots / CLI Output Results

### 10.1 Student Registry Table
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

### 10.2 Academic Report Card
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

### 10.3 Cohort Performance & Grade Histogram
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

## 11. Testing Approach

A comprehensive test suite [`SystemTest.java`](file:///c:/Users/bisen/OneDrive/Desktop/coding/studentmanagement/src/studentmanagement/SystemTest.java) executes automated validation across all modules:

| Test Suite | Target Component | Verifications Checked | Result |
|---|---|---|---|
| **Suite 1** | `InputValidator` | Valid/invalid mark bounds, semester bounds, alphanumeric registration numbers. | **PASS** |
| **Suite 2** | `GradeCalculator` | Mark-to-grade conversions (`A+`, `A`, `B`, `C`, `D`, `F`), GPA boundaries. | **PASS** |
| **Suite 3** | `Student` Model | Total score, average formula, passing criteria, CSV string serialization/deserialization. | **PASS** |
| **Suite 4** | Repository & Service | Record insertion, duplicate prevention, query by RegNo, search filter, statistical aggregation. | **PASS** |

### Test Execution Command:
```bash
java -ea -cp bin studentmanagement.SystemTest
```

---

## 12. Challenges Faced

1. **Handling CSV Special Characters**: Commas in student names or department fields could corrupt standard CSV parsing. Solved by implementing dynamic escape and unescape handlers in `toCsv()` and `fromCsv()`.
2. **Terminal Stream Closing**: Automated pipes or unexpected EOF could throw unhandled `NoSuchElementException`. Solved by implementing `safeReadLine()` wrapper methods inside `InputValidator`.
3. **Cross-Platform Path Handling**: Different operating systems use different path separators (`/` vs `\`). Solved using Java NIO `Paths.get()` and `Path` abstractions.

---

## 13. Learnings & Key Takeaways

- Applying **SOLID principles** significantly improves codebase structure, maintainability, and testability.
- The **Repository pattern** provides clean isolation between business logic and storage mediums.
- Writing automated assertions and test harnesses early prevents regressions during refactoring.
- Console UI applications require careful input sanitization to ensure positive user experiences and crash resilience.

---

## 14. Future Enhancements

1. **Relational Database Migration**: Replace CSV backend with JDBC/H2 or SQLite database integration.
2. **Dynamic Course & Subject Configuration**: Support variable numbers of subjects and credit weightings per course.
3. **Graphical & Web Interface**: Develop a JavaFX desktop GUI or Spring Boot REST API for web-based dashboard analytics.
4. **Role-Based Access Control (RBAC)**: Distinct permissions for Students (view only) and Instructors (edit/grade).

---

## 15. References

1. Oracle Java Documentation: *Java Platform, Standard Edition 17 API Specification*.
2. Bloch, Joshua. *Effective Java (3rd Edition)*. Addison-Wesley Professional, 2018.
3. Gamma, Erich, et al. *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley, 1994.
4. VITyarthi Project Guidelines: *Build Your Own Project Instructions & Submission Rubric*.
