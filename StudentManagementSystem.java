import java.util.*;

class Student {
    private int id;
    private String name;
    private double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + String.format("%.2f", marks);
    }
}
public class StudentManagementSystem {

    private List<Student> students;
    private Scanner scanner;
    private int nextId; 

    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextId = 1;
    }


    // 1. Add Student (Create)
    public void addStudent() {
        System.out.println("\n--- Add New Student ---");
        scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        double marks = -1;
        while (true) {
            System.out.print("Enter student marks: ");
            try {
                marks = scanner.nextDouble();
                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for marks.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Student newStudent = new Student(nextId++, name, marks);
        students.add(newStudent);
        System.out.println("Student added successfully: " + newStudent);
    }

    // 2. View All Students (Read)
    public void viewStudents() {
        System.out.println("\n--- All Student Records ---");
        if (students.isEmpty()) {
            System.out.println("No students enrolled yet.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // 3. Update Student (Update)
    public void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        viewStudents();
        if (students.isEmpty()) {
            return;
        }

        int idToUpdate = -1;
        while (true) {
            System.out.print("Enter ID of student to update: ");
            try {
                idToUpdate = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric ID.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Student studentToUpdate = findStudentById(idToUpdate);

        if (studentToUpdate != null) {
            System.out.println("Current Record: " + studentToUpdate);

            System.out.print("Enter new name (leave blank to keep current: '" + studentToUpdate.getName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                studentToUpdate.setName(newName);
            }

            double newMarks = studentToUpdate.getMarks();
            System.out.print("Enter new marks (leave blank to keep current: " + String.format("%.2f", studentToUpdate.getMarks()) + "): ");
            String marksInput = scanner.nextLine();
            if (!marksInput.trim().isEmpty()) {
                try {
                    double parsedMarks = Double.parseDouble(marksInput);
                    if (parsedMarks >= 0 && parsedMarks <= 100) {
                        newMarks = parsedMarks;
                        studentToUpdate.setMarks(newMarks);
                    } else {
                        System.out.println("Invalid marks. Marks must be between 0 and 100. Marks not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for marks. Marks not updated.");
                }
            }
            System.out.println("Student record updated successfully: " + studentToUpdate);
        } else {
            System.out.println("Student with ID " + idToUpdate + " not found.");
        }
    }

    // 4. Delete Student (Delete)
    public void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        viewStudents();
        if (students.isEmpty()) {
            return;
        }

        int idToDelete = -1;
        while (true) {
            System.out.print("Enter ID of student to delete: ");
            try {
                idToDelete = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric ID.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        Student studentToDelete = findStudentById(idToDelete);

        if (studentToDelete != null) {
            students.remove(studentToDelete);
            System.out.println("Student with ID " + idToDelete + " deleted successfully.");
        } else {
            System.out.println("Student with ID " + idToDelete + " not found.");
        }
    }

    // Helper method to find a student by ID
    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    // Main Application Loop and Menu 
    public void run() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewStudents();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5:
                        System.out.println("Exiting Student Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = 0;
            }
        } while (choice != 5);

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Student Management System Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student Record");
        System.out.println("4. Delete Student Record");
        System.out.println("5. Exit");
        System.out.println("------------------------------------");
    }

    // Main method to start the application
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}