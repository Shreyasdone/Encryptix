import java.util.Random;
import java.util.Scanner;

public class Number_Guessing_Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Welcome To The Number Guessing Game!!!");
        int choice = 1;
        do {
            switch (choice) {
                case 1:
                    int genNum = rand.nextInt(100);
                    int attempts = 5;
                    boolean isWin = false;
                    for (int i = attempts; i > 0; i--) {
                        System.out.print("Guess the number: ");
                        int guess = sc.nextInt();
                        if (guess < genNum) {
                            System.out.println("Higher");
                        } else if (guess > genNum) {
                            System.out.println("Lower");
                        } else {
                            System.out.println("you have Won!!\n You have guessed the number correctly");
                            System.out.println("The number was " + genNum);
                            isWin = true;
                            break;
                        }
                        System.out.println(i - 1 + " attempts remaining");
                    }
                    if (!isWin)
                        System.out.println("The number was " + genNum);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter valid choice");
                    break;
            }
            System.out.println("1.Play Again\n2.Exit");
            choice = sc.nextInt();
        } while (true);
    }
}
