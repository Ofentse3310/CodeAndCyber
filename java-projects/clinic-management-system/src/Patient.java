public class Patient{


    private int iD;
    private String name;
    private int age;
    private String condition;
    private int queueNumber;

    public Patient(int iD, String name, int age, String condition, int queueNumber){
        this.iD = iD;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.queueNumber = queueNumber;
    }

    public int getiD(){
        return iD;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getCondition(){
        return condition;
    }

    public int getQueueNumber(){
        return queueNumber;
    }

    public String toString(){
        return "Queue # "+ queueNumber + " | ID: "+ iD + " | Name: "+ name + " | Age: "+ age + " | Condition: "+ condition;
    }


}
