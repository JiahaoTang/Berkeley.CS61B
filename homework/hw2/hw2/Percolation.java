package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.omg.CORBA.NO_IMPLEMENT;
import sun.util.resources.cldr.ga.CurrencyNames_ga;

public class Percolation {
    private WeightedQuickUnionUF N_grid;
    boolean openFlag[];
    private int N;
    private int siteNumber;

    /*Create N-by-N grid, with all sites initially blocked.
    * Blocked is 0; Full is 1;
    * */
    public Percolation(int N){
        this.N = N;
        N_grid = new WeightedQuickUnionUF(N * N + 2);
        openFlag = new boolean[N * N + 2];
        for(int i = 0; i < N * N; i++){
            openFlag[i] = false;
        }
    }

    //Transfer coordinate into integer.
    private int xyTo1D(int x, int y){
        return N * x + y;
    }
    //Open site (row, col) if it is not open.
    public void open(int row, int col){
        validate(row, col);
        if(!isOpen(row, col)){
            siteNumber += 1;
            openFlag[xyTo1D(row, col)] = true;
            if(row - 1 >= 0&&isOpen(row - 1, col)) N_grid.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            if(row + 1 < N &&isOpen(row + 1, col)) N_grid.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            if(col - 1 >= 0&&isOpen(row, col - 1)) N_grid.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            if(col + 1 < N &&isOpen(row, col + 1)) N_grid.union(xyTo1D(row, col + 1), xyTo1D(row, col));
            if(row == 0) N_grid.union(xyTo1D(row, col), N * N);
            if(row == N - 1) N_grid.union(xyTo1D(row, col), N * N + 1);
        }
    }

    //Is the site open?
    public boolean isOpen(int row, int col){
        validate(row, col);
        return openFlag[xyTo1D(row, col)] == true;
    }

    //Is the site Full?
    public boolean isFull(int row, int col){
        validate(row, col);
        return N_grid.connected(xyTo1D(row, col), N * N);
    }

    //Number of open site.
    public int numberOfOpenSites(){
        return siteNumber;
    }

    //Does the system percolate?
    public boolean percolates(){
        return N_grid.connected(N * N, N * N + 1);
    }

    //Ensure input parameter row and col is valid
    public void validate(int row, int col){
        if(row > N - 1 || col > N - 1) throw new IndexOutOfBoundsException("The Index is too high.");
        else if(row < 0 || col < 0) throw new IllegalArgumentException("The Index is an error.");
    }

    public static void main(String[] args){
        Percolation N55 = new Percolation(10);
        /*
        N55.open(0, 2);
        System.out.print("isOpen 1 2 ");
        System.out.println(N55.isOpen(0, 2));
        System.out.print("isFull? 0 2 ");
        System.out.println(N55.isFull(0, 2));
        N55.open(2, 2);
        System.out.print("isOpen 2 2 ");
        System.out.println(N55.isOpen(2, 2));
        System.out.print("isFull? 2 2 ");
        System.out.println(N55.isFull(2, 2));
        N55.open(1, 2);
        System.out.print("isOpen 1 2 ");
        System.out.println(N55.isOpen(1, 2));
        System.out.print("isFull? 1 2 ");
        System.out.println(N55.isFull(1, 2));
        System.out.print("isFull? 2 2 ");
        System.out.println(N55.isFull(2, 2));
        */
    }
}                       
