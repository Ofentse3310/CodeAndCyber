import java.util.ArrayList;

public class Queue {

    private final ArrayList<Patient> waitingList = new ArrayList<>();

    public void addPatient(Patient patient){

        int lastEmergencyPosition;

        if(patient.getCondition().equals("emergency")){
            lastEmergencyPosition = -1;

            for(int i = 0; i < waitingList.size(); i++){
                if(waitingList.get(i).getCondition().equals("emergency")){
                    lastEmergencyPosition = i;
                }
            }
            waitingList.add(lastEmergencyPosition + 1, patient);
        } else{
            waitingList.add(patient);
        }
        System.out.println("Patient added to queue");
    }
    public Patient callNextPatient(){

        if(waitingList.isEmpty()){
            System.out.println("No patients in queue");
            return null;
        }
        Patient next = waitingList.removeFirst();
        System.out.println("Now serving: "+ next);
        return next;
    }

    public void viewQueue(){

        if(waitingList.isEmpty()){
            System.out.println("Queue is empty");
            return;
        }
        System.out.println("---- Current Queue ----");
        for(int i = 0; i < waitingList.size(); i++){
            Patient p = waitingList.get(i);
            System.out.println((i + 1) + ". " + p);
        }
    }

    public int getSize(){
        return waitingList.size();
    }

    public boolean isEmpty(){
        return waitingList.isEmpty();
    }
}
