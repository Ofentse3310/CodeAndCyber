
import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {

    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("studentManagement.Student Management System");
            System.out.println("1. Add studentManagement.Student");
            System.out.println("2. View Students");
            System.out.println("3. Search studentManagement.Student");
            System.out.println("4. Delete studentManagement.Student");
            System.out.println("5. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please provide studentManagement.Student's ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Please provide studentManagement.Student's name: ");
                    String name = scanner.nextLine();

                    System.out.println("Please provide the studentManagement.Student's age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Please provide the studentManagement.Student's course:");
                    String course = scanner.nextLine();

                    Student student = new Student(id, name, age, course);
                    students.add(student);
                    System.out.println("studentManagement.Student added successfully!");
                    break;

                case 2:
                    System.out.println("'\n--- List of Students ---");
                    for (Student s : students) {
                        s.displayInfo();
                    }
                    break;
                case 3:
                    System.out.println("Please provide the studentManagement.Student's ID: ");
                    int searchID = scanner.nextInt();
                    boolean found = false;
                    for (Student s : students) {
                        if (s.studentID() == searchID) {
                            s.displayInfo();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("studentManagement.Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter studentManagement.Student ID to delete: ");
                    int deleteID = scanner.nextInt();
                    boolean removed = false;
                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).studentID() == deleteID) {
                            students.remove(i);
                            removed = true;
                            System.out.println("studentManagement.Student deleted successfully.");
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("studentManagement.Student not found.");
                    }
                    break;
                case 5:
                    Student.saveStudents(students);
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Invalid option");
            }


        }
        while (choice != 5);
        scanner.close();
    }
}




