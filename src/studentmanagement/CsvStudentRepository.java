package studentmanagement;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CSV file implementation of StudentRepository.
 */
public class CsvStudentRepository implements StudentRepository {

    private final Path filePath;
    private final Map<String, Student> cache = new LinkedHashMap<>();
    private static final String CSV_HEADER = "RegNo,Name,Department,Semester,Mark1,Mark2,Mark3";

    public CsvStudentRepository(String filePathStr) {
        this.filePath = Paths.get(filePathStr);
        initFileAndLoad();
    }

    private synchronized void initFileAndLoad() {
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                    writer.write(CSV_HEADER);
                    writer.newLine();
                }
            } else {
                loadFromCsv();
            }
        } catch (IOException e) {
            System.err.println("Error initializing CSV repository: " + e.getMessage());
        }
    }

    private synchronized void loadFromCsv() {
        cache.clear();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                Student student = Student.fromCsv(line);
                if (student != null && !student.getRegNo().isEmpty()) {
                    cache.put(student.getRegNo().toUpperCase(Locale.ROOT), student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    private synchronized void saveToCsv() {
        try {
            if (filePath.getParent() != null && !Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write(CSV_HEADER);
                writer.newLine();
                for (Student student : cache.values()) {
                    writer.write(student.toCsv());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    @Override
    public synchronized void save(Student student) {
        if (student == null || student.getRegNo().isEmpty()) {
            return;
        }
        cache.put(student.getRegNo().toUpperCase(Locale.ROOT), student);
        saveToCsv();
    }

    @Override
    public synchronized Optional<Student> findByRegNo(String regNo) {
        if (regNo == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cache.get(regNo.trim().toUpperCase(Locale.ROOT)));
    }

    @Override
    public synchronized List<Student> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public synchronized boolean update(Student student) {
        if (student == null || !cache.containsKey(student.getRegNo().toUpperCase(Locale.ROOT))) {
            return false;
        }
        cache.put(student.getRegNo().toUpperCase(Locale.ROOT), student);
        saveToCsv();
        return true;
    }

    @Override
    public synchronized boolean deleteByRegNo(String regNo) {
        if (regNo == null) {
            return false;
        }
        Student removed = cache.remove(regNo.trim().toUpperCase(Locale.ROOT));
        if (removed != null) {
            saveToCsv();
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean existsByRegNo(String regNo) {
        if (regNo == null) {
            return false;
        }
        return cache.containsKey(regNo.trim().toUpperCase(Locale.ROOT));
    }

    @Override
    public synchronized List<Student> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lower = name.trim().toLowerCase(Locale.ROOT);
        return cache.values().stream()
                .filter(s -> s.getName().toLowerCase(Locale.ROOT).contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Student> findByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lower = department.trim().toLowerCase(Locale.ROOT);
        return cache.values().stream()
                .filter(s -> s.getDepartment().toLowerCase(Locale.ROOT).contains(lower))
                .collect(Collectors.toList());
    }
}
