import java.io.*;
import java.util.ArrayList;

public class FileManager {

    static String File_Name = "patients.txt";

    public static void savePatients(ArrayList<Patient> patientList){

        try{
            PrintWriter writer =  new PrintWriter(new FileWriter(File_Name));

            for(Patient p : patientList){
                writer.println(p.getiD() +","+ p.getName() +","+ p.getAge() +","+ p.getCondition() +","+ p.getQueueNumber());
            }
            writer.close();

            System.out.println("Patients saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving patients");
        }
    }

    public static ArrayList<Patient> loadPatients(){

        ArrayList<Patient> loadedPatients = new ArrayList<>();

        try{
            File file = new File(File_Name);
            if(!file.exists()){
                return loadedPatients;
            }

            BufferedReader reader = new BufferedReader(new FileReader(File_Name));

            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String emergency = parts[3];
                int queueNumber = Integer.parseInt(parts[4]);

                Patient p = new Patient(id, name, age, emergency, queueNumber);
                loadedPatients.add(p);
            }
            reader.close();
            }
        catch (Exception e) {
            System.out.println("Error loading patients");;
        }
        return loadedPatients;
    }

}
