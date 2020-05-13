import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(final String[] args) throws IOException {
        final Scanner keyboard = new Scanner(System.in);
        System.out.println("What level would you like to play?");
        System.out.println("1) Easy");
        System.out.println("2) Medium");
        System.out.println("3) Hard");
        System.out.println("4) Expert");
        System.out.println("5) Hell");
        System.out.print("Type the number representing your answer: ");
        final int level = keyboard.nextInt();
        keyboard.nextLine();
        final Minesweeper game = new Minesweeper();
        game.play(level);
        System.out.println("Thanks for playing!");
        System.out.println("Press ENTER to exit the program.");
        keyboard.nextLine();
        keyboard.close();
    }
}
