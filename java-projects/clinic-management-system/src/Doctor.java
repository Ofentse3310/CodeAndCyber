import java.util.ArrayList;

public class Doctor {

    private final String name;
    private final ArrayList<Patient> patientHistory = new ArrayList<>();

    public Doctor(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void servePatient(Patient patient){
        patientHistory.add(patient);
        System.out.println("Dr. "+ name +" is now serving: "+ patient);
    }

    public void viewHistory(){
        if(patientHistory.isEmpty()){
            System.out.println("No patients served yet");
            return;
        }
        System.out.println("--- Patient History for Dr. "+ name + " ---");
        for(Patient p : patientHistory){
            System.out.println(p);
        }
    }
}
