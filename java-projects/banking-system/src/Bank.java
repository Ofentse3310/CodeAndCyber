
import java.util.Scanner;

public class Bank {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        BankAccount account = new BankAccount("David",54321,0.00);

        int choice;

        do{
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check balance");
            System.out.println("4. Exit");

            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Enter deposit amount: ");
                    double deposit = scanner.nextInt();
                    account.deposit(deposit);
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount: ");
                    double withdraw = scanner.nextInt();
                    account.withdraw(withdraw);
                    break;
                case 3:
                    account.checkBalance();
                    break;

                case 4:
                    System.out.println("Thanks for banking with us.");
                    break;
                default:
                    System.out.println("Invalid option!");
            }


        }while(choice != 4);

        scanner.close();
    }
}
