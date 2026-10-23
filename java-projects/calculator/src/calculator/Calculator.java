package calculator;

import java.util.Scanner;

public class Calculator {

    public static void main(String [] args){

        Scanner scanner = new Scanner(System.in);
        int num1;
        int num2;
        int choice;

        do{
            System.out.println("\n--- Choose an operator ---");
            System.out.println("1. Addition(+):");
            System.out.println("2. Subtraction(-): ");
            System.out.println("3. Multiplication(*): ");
            System.out.println("4. Division(/): ");
            System.out.println("5. Exit.");

            choice = scanner.nextInt();

            switch (choice){

                case 1:
                    System.out.print("Enter your first number: ");
                    num1 = scanner.nextInt();

                    System.out.print("Enter your second number: ");
                    num2 = scanner.nextInt();

                    System.out.println(num1 +" + "+ num2 +" = "+ (num1 + num2));
                    break;

                case 2:
                    System.out.print("Enter your first number: ");
                    num1 = scanner.nextInt();

                    System.out.print("Enter your second number: ");
                    num2 = scanner.nextInt();

                    System.out.println(num1 +" - "+ num2 +" = "+ (num1 - num2) );
                    break;

                case 3:
                    System.out.print("Enter your first number: ");
                    num1 = scanner.nextInt();

                    System.out.print("Enter your second number: ");
                    num2 = scanner.nextInt();

                    System.out.println(num1 +" * "+ num2 +" = "+ (num1 * num2));
                    break;

                case 4:
                    System.out.print("Enter your first number: ");
                    num1 = scanner.nextInt();

                    System.out.print("Enter your second number: ");
                    num2 = scanner.nextInt();

                    if(num2 == 0){
                        System.out.println("Error!! You can not divide by zero.");
                        break;

                    }else {
                        System.out.println(num1 + " / " + num2 + " = " + (num1 / num2));
                        break;

                    }
                case 5:
                    System.out.println("Thanks for using our calculator.");
                    break;

                default:
                    System.out.println("Invalid input!!!");

            }

        }while (choice != 5);

    }
}
