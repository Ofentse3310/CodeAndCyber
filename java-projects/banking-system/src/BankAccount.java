
public class BankAccount {


    private final String accountHolder;
    private final int accountNumber;
    private double balance;

    public BankAccount(String accountHolder, int accountNumber, double balance){
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount){
        balance += amount;
        System.out.println("Deposit Successful. New Balance: "+ balance);
    }

    public void withdraw(double amount){
        if(amount <= balance){
            balance -= amount;
            System.out.println("Withdrawal Successful. New Balance: "+ balance);
        }else{
            System.out.println("Insufficient Funds!!!");
        }
    }

    public void checkBalance(){
        System.out.println("Current Balance: "+ balance);
    }
    public String toString(){
        return accountHolder + "," + accountNumber + "," + balance;
    }

}


