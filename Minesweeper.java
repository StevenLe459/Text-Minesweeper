import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Minesweeper {
    String command;
    int r;
    int c;
    char f;

    public void play(int level) throws IOException {
        Path file;
        Scanner keyboard;
        Grid grid;
        block34 : {
            int numMines;
            int row;
            int column;
            keyboard = new Scanner(System.in);
            switch (level) {
                case 1: {
                    row = 10;
                    column = 10;
                    numMines = 10;
                    break;
                }
                case 2: {
                    row = 14;
                    column = 18;
                    numMines = 40;
                    break;
                }
                case 3: {
                    row = 16;
                    column = 30;
                    numMines = 99;
                    break;
                }
                case 4: {
                    row = 25;
                    column = 40;
                    numMines = 250;
                    break;
                }
                case 5: {
                    row = 50;
                    column = 50;
                    numMines = 750;
                    break;
                }
                default: {
                    row = 14;
                    column = 18;
                    numMines = 40;
                }
            }
            grid = new Grid(row, column, numMines);
            file = Paths.get("Minesweeper.txt", new String[0]);
            Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
            System.out.println("Instructions:");
            System.out.println("The goal of the game is to clear the area without detonating any of the hidden mines.");
            System.out.println("The Minesweeper board will be displayed in the text file \"Minesweeper.txt\".");
            System.out.println("The file needs to refresh every time, so please close the file before typing in a command and reopen it after.");
            System.out.println("When prompted, type in the following commands in the terminal for certain actions:");
            System.out.println("fr#c# - flag: the letter 'f' followed by the letter 'r', the row number, the letter 'c', and the column number will place a flag at that spot.");
            System.out.println("dr#c# - de-flag: the letter 'd' followed by the letter 'r', the row number, the letter 'c', and the column number will remove the flag at the specified spot.");
            System.out.println("or#c# - open: the letter 'o' followed by the letter 'r', the row number, the letter 'c', and the column number will open up the square at the specified spot.");
            System.out.println("For your convenience, these instructions are contained in the file \"Instructions.txt.\"");
            System.out.print("Press ENTER to continue.");
            keyboard.nextLine();
            try {
                try {
                    System.out.print("Please type in your starting open command: ");
                    this.command = keyboard.nextLine();
                    this.r = this.getRowNum(this.command);
                    this.c = this.getColumnNum(this.command);
                    this.f = this.command.charAt(0);
                }
                catch (Exception e) {
                    System.out.println("Invalid command. Please try again.");
                    System.out.println("Please type in your starting command: ");
                    this.command = keyboard.nextLine();
                    this.r = this.getRowNum(this.command);
                    this.c = this.getColumnNum(this.command);
                    this.f = this.command.charAt(0);
                    grid.startClick(this.r, this.c);
                    Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
                    break block34;
                }
            }
            catch (Throwable throwable) {
                grid.startClick(this.r, this.c);
                Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
                throw throwable;
            }
            grid.startClick(this.r, this.c);
            Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
        }
        do {
            try {
                try {
                    System.out.print("Please type in your command: ");
                    this.command = keyboard.nextLine();
                    this.r = this.getRowNum(this.command);
                    this.c = this.getColumnNum(this.command);
                    this.f = this.command.charAt(0);
                }
                catch (Exception e) {
                    System.out.println("Invalid command. Please try again.");
                    System.out.println("Please type in your starting command: ");
                    this.command = keyboard.nextLine();
                    this.r = this.getRowNum(this.command);
                    this.c = this.getColumnNum(this.command);
                    this.f = this.command.charAt(0);
                    if (this.f == 'f') {
                        grid.flag(this.r, this.c);
                    } else if (this.f == 'd') {
                        grid.deflag(this.r, this.c);
                    } else if (this.f == 'o') {
                        grid.click(this.r, this.c);
                    }
                    Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
                    continue;
                }
            }
            catch (Throwable throwable) {
                if (this.f == 'f') {
                    grid.flag(this.r, this.c);
                } else if (this.f == 'd') {
                    grid.deflag(this.r, this.c);
                } else if (this.f == 'o') {
                    grid.click(this.r, this.c);
                }
                Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
                throw throwable;
            }
            if (this.f == 'f') {
                grid.flag(this.r, this.c);
            } else if (this.f == 'd') {
                grid.deflag(this.r, this.c);
            } else if (this.f == 'o') {
                grid.click(this.r, this.c);
            }
            Files.write(file, grid.toStringIterable(), Charset.forName("UTF-8"), new OpenOption[0]);
        } while (!grid.wasNuked() && !grid.hasWon());
        if (grid.wasNuked()) {
            System.out.println("You lost!");
        }
        if (grid.hasWon()) {
            System.out.println("You won!");
        }
        keyboard.close();
    }

    public int getRowNum(String cmd) {
        return Integer.valueOf(cmd.substring(cmd.indexOf(114) + 1, cmd.indexOf(99))) - 1;
    }

    public int getColumnNum(String cmd) {
        return Integer.valueOf(cmd.substring(cmd.indexOf(99) + 1)) - 1;
    }
}