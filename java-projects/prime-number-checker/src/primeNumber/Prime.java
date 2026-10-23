package primeNumber;

import java.util.Scanner;

public class Prime {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Enter a number to check if is a primeNumber.Prime Number: ");

            int number = scanner.nextInt();

            if (isPrime(number)) {
                System.out.println(number + " is prime number");
            } else {
                System.out.println(number + " is not a prime number");
            }
        } catch (Exception e) {
            System.out.println("Invalid input!!! Please enter a valid integer.");;
        }
        scanner.close();
    }
    public static boolean isPrime(int n){
        if(n <= 1){
           return false;
        }
        for(int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }
}
