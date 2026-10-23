import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static Doctor doctor = null;
    static FileManager fileManager = new FileManager();
    static Queue queue = new Queue();
    static ArrayList<Patient> patientList = new ArrayList<>();
    static int nextId = 1;
    static int nextQueueNumber = 1;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("     WELCOME TO SMART CLINIC SYSTEM ");
        System.out.println("=======================================");

        String doctorName;
        while (true) {
            System.out.println("Enter doctor's name: ");
            doctorName = scanner.nextLine().trim();
            if (doctorName.matches("[a-zA-Z ]+")) {
                break;
            }else{
                System.out.println("Invalid name. Please use letter only");
            }
        }
        doctor = new Doctor(doctorName);
        System.out.println("Welcome Dr. "+ doctorName);

        boolean running = true;

        ArrayList<Patient> loadedList = fileManager.loadPatients();
        for(Patient p : loadedList){
            patientList.add(p);
            queue.addPatient(p);
        }

        if(!loadedList.isEmpty()){
            nextId = loadedList.size() + 1;
            nextQueueNumber = loadedList.size() + 1;
        }

        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            if (choice == 1) {
                registerPatient();
            }
            else if (choice == 2) {
                viewAllPatients();
            }
            else if(choice == 3){
                callNextPatient();
            }
            else if(choice == 4){
                viewQueue();
            }
            else if(choice == 5){
                doctorServeNext();
            }
            else if(choice == 6){
                viewDoctorHistory();
            }
            else if(choice == 7){
                searchPatient();
            }
            else if (choice == 8) {
                fileManager.savePatients(patientList);
                System.out.println("Goodbye!!! Thanks for using our app");
                running = false;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
    }

        public static void showMenu(){
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Call Next Patient");
            System.out.println("4. View Queue");
            System.out.println("5. Doctor Serve Next Patient");
            System.out.println("6. View Doctor History");
            System.out.println("7. Search Patient by Name");
            System.out.println("8. Exit");
        }

        public static void registerPatient(){

            System.out.println("\n--- Register New Patient ---");

            System.out.print("Enter the name of the patient: ");
            String name = scanner.nextLine();
            if(!name.matches("[a-zA-Z ]+")){
                System.out.println("Invalid name. Please use letters only.");
                return;
            }

            int age = getIntInput("Enter the patient age: ");

            System.out.print("Is it an emergency?(yes / no): ");
            String isEmergency = scanner.nextLine().trim().toLowerCase();

            String condition;

            if(isEmergency.equals("yes")){
                condition = "emergency";
            }else{
                condition = "normal";
            }
            Patient newPatient = new Patient(nextId, name, age, condition, nextQueueNumber);
            patientList.add(newPatient);
            queue.addPatient(newPatient);

            System.out.println("Patient successfully registered!");
            System.out.println(newPatient);

            nextId++;
            nextQueueNumber++;
        }

        public static void viewAllPatients(){

            if(patientList.isEmpty()){
                System.out.println("No patients registered yet.");
                return;
            }
            for(Patient p : patientList){
                System.out.println(p);
            }
        }

        public static int getIntInput(String prompt){
            while(true){
                System.out.print(prompt);
                try{
                    int value = Integer.parseInt(scanner.nextLine().trim());
                    return value;
                }catch(NumberFormatException e){
                    System.out.println("Please enter a valid number.");
                }
            }
        }

        public static void callNextPatient(){
            queue.callNextPatient();
        }

        public static void viewQueue(){
            queue.viewQueue();
        }

        public static void doctorServeNext(){
             Patient  returnedPatient = queue.callNextPatient();

             if(returnedPatient != null){
                 doctor.servePatient(returnedPatient);
             }
        }
        public static void viewDoctorHistory(){
            doctor.viewHistory();
        }

        public static void searchPatient(){
            System.out.println("Enter name to search: ");
            String searchName = scanner.nextLine();
            if(!searchName.matches("[a-zA-Z ]+")){
                System.out.println("Invalid name. Please use letters only.");
                return;
            }

            boolean found = false;

            for(Patient p : patientList){
                if(p.getName().toLowerCase().contains(searchName.toLowerCase())){
                    System.out.println(p);
                    found = true;
                }
            }
            if(!found){
                System.out.println("No patient found with that name");
            }
        }

}
