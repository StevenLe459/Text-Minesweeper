import java.util.Iterator;
import java.util.ArrayList;

public class Grid
{
    private int rowSize;
    private int columnSize;
    private int numMines;
    private Cell[][] board;
    private ArrayList<Integer> mines;
    private ArrayList<Integer> safeSpace;
    private boolean nuked;
    private boolean won;
    
    public Grid(final int row, final int column, final int m) {
        this.nuked = false;
        this.won = false;
        this.rowSize = row;
        this.columnSize = column;
        this.numMines = m;
        this.board = new Cell[row][column];
        this.mines = new ArrayList<Integer>();
        this.safeSpace = new ArrayList<Integer>();
        this.generateMines();
        this.generateSafeSpace();
        for (int r = 0; r < this.rowSize; ++r) {
            for (int c = 0; c < this.columnSize; ++c) {
                if (this.mines.contains(this.cellToInt(r, c))) {
                    this.board[r][c] = new Cell(true);
                }
                else {
                    this.board[r][c] = new Cell(false);
                }
            }
        }
        this.numberize();
    }
    
    public void generateMines() {
        for (int num = 1; num <= this.numMines; ++num) {
            final int temp = (int)(Math.random() * (this.rowSize * this.columnSize - 1));
            if (!this.mines.contains(temp)) {
                this.mines.add(temp);
            }
        }
    }
    
    public void generateSafeSpace() {
        for (int i = 0; i <= this.rowSize * this.columnSize - 1; ++i) {
            if (!this.mines.contains(i)) {
                this.safeSpace.add(i);
            }
        }
    }
    
    public void numberize() {
        for (int j = 0; j < this.rowSize; ++j) {
            for (int k = 0; k < this.columnSize; ++k) {
                this.board[j][k].setNum(this.minesAround(j, k));
            }
        }
    }
    
    private int cellToInt(final int r, final int c) {
        return r * this.columnSize + c;
    }
    
    private int minesAround(final int r, final int c) {
        int num = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (r + i >= 0 && r + i < this.rowSize && c + j >= 0 && c + j < this.columnSize && (r != 0 || c != 0) && this.board[r + i][c + j].hasMine()) {
                    ++num;
                }
            }
        }
        return num;
    }
    
    public void flag(final int r, final int c) {
        this.board[r][c].addFlag();
    }
    
    public void deflag(final int r, final int c) {
        this.board[r][c].removeFlag();
    }
    
    public void startClick(final int r, final int c) {
        if (this.board[r][c].hasMine() || this.board[r][c].howMany() > 0) {
            final ArrayList<Integer> m = new ArrayList<Integer>();
            final ArrayList<Integer> a = new ArrayList<Integer>();
            for (int i = -1; i <= 1; ++i) {
                if (r + i >= 0 && r + i < this.rowSize) {
                    for (int j = -1; j <= 1; ++j) {
                        if (c + j >= 0 && c + j < this.rowSize) {
                            if (this.board[r + i][c + j].hasMine()) {
                                m.add(this.cellToInt(r + i, c + j));
                            }
                            a.add(this.cellToInt(r + i, c + j));
                        }
                    }
                }
            }
            for (final int mystery : m) {
                this.board[mystery / this.columnSize][mystery % this.columnSize].setMine(false);
                int temp;
                int index;
                for (temp = a.get(0); a.contains(temp); temp = this.safeSpace.get(index)) {
                    index = (int)(Math.random() * (this.safeSpace.size() - 1));
                }
                this.board[temp / this.columnSize][temp % this.columnSize].setMine(true);
                this.mines.remove(new Integer(mystery));
                this.mines.add(new Integer(temp));
                this.safeSpace.remove(new Integer(temp));
                this.safeSpace.add(new Integer(mystery));
            }
            this.numberize();
            this.click(r, c);
        }
        else {
            this.click(r, c);
        }
    }
    
    public void click(final int r, final int c) {
        if (this.board[r][c].hasMine()) {
            this.nuked = true;
            for (final int i : this.mines) {
                this.board[i / this.columnSize][i % this.columnSize].bombed();
                this.board[i / this.columnSize][i % this.columnSize].removeFlag();
            }
        }
        else if (this.board[r][c].howMany() == 0) {
            this.findClump(r, c);
        }
        else {
            this.board[r][c].print();
        }
    }
    
    public void findClump(final int r, final int c) {
        final boolean isInBound = r >= 0 && r < this.rowSize && c >= 0 && c < this.columnSize;
        if (isInBound && !this.board[r][c].hasMine() && !this.board[r][c].isPrinted() && this.board[r][c].howMany() == 0) {
            this.board[r][c].print();
            this.findClump(r - 1, c - 1);
            this.findClump(r - 1, c);
            this.findClump(r - 1, c + 1);
            this.findClump(r, c - 1);
            this.findClump(r, c + 1);
            this.findClump(r + 1, c - 1);
            this.findClump(r + 1, c);
            this.findClump(r + 1, c + 1);
        }
        else if (isInBound && !this.board[r][c].hasMine() && !this.board[r][c].isPrinted() && this.board[r][c].howMany() > 0) {
            this.board[r][c].print();
        }
    }
    
    public boolean wasNuked() {
        return this.nuked;
    }
    
    public boolean hasWon() {
        this.won = true;
        for (final int i : this.safeSpace) {
            if (!this.board[i / this.columnSize][i % this.columnSize].isPrinted()) {
                this.won = false;
                break;
            }
        }
        return this.won;
    }
    
    public ArrayList<String> toStringIterable() {
        final ArrayList<String> temp = new ArrayList<String>();
        String mystery = "   ";
        for (int i = 0; i < this.columnSize; ++i) {
            mystery = String.valueOf(mystery) + " " + String.format("%3s", "c" + Integer.toString(i + 1)) + " ";
        }
        temp.add(mystery);
        for (int r = 0; r < this.rowSize; ++r) {
            String dummy = String.format("%3s", "r" + Integer.toString(r + 1));
            for (int c = 0; c < this.columnSize; ++c) {
                dummy = String.valueOf(dummy) + this.board[r][c].toString();
            }
            temp.add(dummy);
            temp.add(" ");
        }
        return temp;
    }
}