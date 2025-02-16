package game;

public class Stone {
    private int color;
    private int row;
    private int col;
    private int step;

    public int getStoneStep(){return this.step;}
    public int getStoneCol(){return this.col;}
    public int getStoneRow(){return this.row;}
    public int getStoneColor(){return this.color;}
    public Stone setStoneColor(int color){this.color = color; return this;}
    public Stone setStoneCol(int c, int bsize){if (c > 0 && c <= bsize){this.col = c;} return this;}
    public Stone setStoneRow(int r, int bsize){if (r > 0 && r <= bsize){this.row = r;} return this;}
    public Stone setStoneStep(int num){this.step = num + 1; return this;}
}