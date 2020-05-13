public class Cell
{
    private boolean mine;
    private boolean flag;
    private int numBombs;
    private boolean printed;
    private boolean bombed;
    
    public Cell(final boolean mine) {
        this.flag = false;
        this.numBombs = 0;
        this.printed = false;
        this.bombed = false;
        this.mine = mine;
    }
    
    public void setMine(final boolean b) {
        this.mine = b;
    }
    
    public void addFlag() {
        this.flag = true;
    }
    
    public void removeFlag() {
        this.flag = false;
    }
    
    public void setNum(final int n) {
        this.numBombs = n;
    }
    
    public void print() {
        this.printed = true;
    }
    
    public void bombed() {
        this.bombed = true;
    }
    
    public boolean hasMine() {
        return this.mine;
    }
    
    public boolean hasFlag() {
        return this.flag;
    }
    
    public int howMany() {
        return this.numBombs;
    }
    
    public boolean isPrinted() {
        return this.printed;
    }
    
    @Override
    public String toString() {
        if (this.flag) {
            return " [F] ";
        }
        if (this.bombed) {
            return " [*] ";
        }
        if (!this.printed) {
            return " [ ] ";
        }
        if (this.howMany() > 0) {
            return " [" + Integer.toString(this.howMany()) + "] ";
        }
        return " [@] ";
    }
}