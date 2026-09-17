# Project Statement & Problem Definition

## 1. Problem Statement
Academic institutions, university departments, and course instructors frequently handle student academic records, marks evaluation, and grade computation across multiple semesters. Manual calculation and fragmented spreadsheet recording lead to data discrepancies, delayed grade processing, computational errors in calculating GPAs and class statistics, and an absence of structured persistent storage.

There is a distinct need for a lightweight, command-line accessible, and persistent **Student Management & Academic Analytics System** built in Java that automates student profile management, standardizes grading rules, calculates class metrics, and ensures reliable data persistence.

---

## 2. Scope of the Project

The scope of this project encompasses:
- **Demographic & Academic Profile Management**: Capturing registration numbers, student names, academic departments, semester numbers, and individual subject marks.
- **Grading & GPA Computation Engine**: Standardizing mark-to-grade conversions (`A+`, `A`, `B`, `C`, `D`, `F`), calculating GPAs on a 4.0 scale, and evaluating pass/fail status.
- **Cohort Analytics & Distribution**: Calculating aggregate class metrics such as total enrollment, overall pass rate, class average score, top/lowest performers, and graphical grade distribution histograms.
- **Search, Filter & Sorting**: Enabling multi-field case-insensitive searching and flexible sorting by name, registration number, or academic performance.
- **Data Persistence & File Export**: Providing seamless disk persistence using structured CSV format (`data/students.csv`) and report export to readable text files.

*Out of Scope for Current Version*: Multi-user role-based authentication, database servers (SQL/NoSQL), and graphical web dashboards (planned for future phases).

---

## 3. Target Users

1. **Course Instructors & Professors**: Need to record semester test marks, evaluate student performance, and view grade distributions.
2. **Department Academic Coordinators**: Need a centralized summary of cohort performance, toppers list, and pass rates.
3. **Institutional Administrators**: Need structured exportable summaries and reliable offline student record keeping.

---

## 4. High-Level Features

| Feature ID | Feature Name | Description |
|---|---|---|
| **F-01** | Student Profile Creation | Allows adding students with validated registration number, name, department, semester, and 3 subject marks. |
| **F-02** | Record Retrieval & Listing | Displays all student records in an aligned tabular ASCII layout. |
| **F-03** | Multi-Field Search | Searches records across Registration Number, Full Name, and Department. |
| **F-04** | Record Modification | Supports editing personal details, department, semester, and marks with instant grade recalculation. |
| **F-05** | Record Deletion | Safely removes records from memory and CSV storage with user confirmation. |
| **F-06** | Student Report Card | Generates a formatted academic transcript card showing subject scores, GPA, percentage, and remarks. |
| **F-07** | Class Analytics Engine | Computes class averages, highest/lowest scores, pass rates, and ASCII grade histograms. |
| **F-08** | Multi-Criteria Sorting | Sorts student lists by Name (A-Z), Reg No, or Average Score (Highest/Lowest). |
| **F-09** | Report File Export | Generates an external summary text report with timestamps for archiving. |
| **F-10** | CSV Disk Persistence | Automatically persists all CRUD mutations to disk without data loss between program executions. |
