package studentManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public record Student(int studentID, String name, int age, String course) {

    public String toFileString() {
        return studentID + "," + name + "," + age + "," + course;
    }

    public void displayInfo() {
        System.out.println("studentManagement.Student ID: " + studentID + ", Name: " + name + ", Age: " + age + ", Course: " + course);
    }

    public static void saveStudents(ArrayList<Student> students) {

        try {
            FileWriter writer = new FileWriter("students.txt");

            for (Student s : students) {
                writer.write(s.toFileString() + "\n");
            }

            writer.close();
            System.out.println("Students saved to file.");

        } catch (IOException e) {
            System.out.println("Error saving students.");
        }
    }

}
