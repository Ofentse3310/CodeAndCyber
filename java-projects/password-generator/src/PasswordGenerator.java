
import java.util.Scanner;

public class PasswordGenerator {

    public static boolean verifyPassword(String password) {

        if(password.length() < 8) {
            return false;
        }

        int lowerCaseCounter = 0;
        int upperCaseCounter = 0;
        int digitCounter = 0;
        int specialCaseCounter = 0;


        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                upperCaseCounter++;
            }else if(Character.isLowerCase(character)) {
                lowerCaseCounter++;
            }else if(Character.isDigit(character)) {
                digitCounter++;
            }else {
                specialCaseCounter++;
            }
        }
        return lowerCaseCounter >= 1 && upperCaseCounter >= 1 && digitCounter >= 1 && specialCaseCounter >= 1;
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        if(verifyPassword(password)){
            System.out.println("Password is valid");
        }else{
            System.out.println("Invalid password");
        }
    }
}




