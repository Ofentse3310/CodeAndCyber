package miniProjects;

import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int randomNumber = (int) (Math.random() * 100);
        int attempts = 0;
        int userGuess;
        boolean guessedCorrectly = false;

            while (!guessedCorrectly) {

                System.out.println("Enter your guess (1-100): ");

                if(!scanner.hasNextInt()){
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    continue;
                }
                userGuess = scanner.nextInt();

                if(userGuess < 1 || userGuess > 100){
                    System.out.println("Number must be between 1 and 100");
                    continue;
                }
                attempts ++;

                if (userGuess == randomNumber) {
                    System.out.println("Correct! You guessed the number.");
                    System.out.println("Attempts: " + attempts);
                    guessedCorrectly = true;
                }
                else if (userGuess < randomNumber) {
                    System.out.println("Too low! Try again.");
                }
                else {
                    System.out.println("Too high! Try again.");
                }

            }
            scanner.close();
    }
}
