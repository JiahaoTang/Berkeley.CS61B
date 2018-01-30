import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture pic;

    public SeamCarver(Picture picture){
        pic = picture;
    }

    public Picture picture(){
        return pic;
    }

    public int width(){
        return pic.width();
    }

    public int height(){
        return pic.height();
    }

    public double energy(int x, int y){

        int left, right, upper, lower;

        if(x - 1 < 0){
            left = width() - 1;
        }else{
            left = x - 1;
        }

        if(x + 1 > width() - 1){
            right = 0;
        }else{
            right = x + 1;
        }

        if(y - 1 < 0){
            upper = height() - 1;
        }else{
            upper = y - 1;
        }

        if(y + 1 > height() - 1){
            lower = 0;
        }else{
            lower = y + 1;
        }

        int deltaXR = Math.abs(pic.get(left, y).getRed() - pic.get(right, y).getRed());
        int deltaXG = Math.abs(pic.get(left, y).getGreen() - pic.get(right, y).getGreen());
        int deltaXB = Math.abs(pic.get(left, y).getBlue() - pic.get(right, y).getBlue());
        int deltaYR = Math.abs(pic.get(x, upper).getRed() - pic.get(x, lower).getRed());
        int deltaYG = Math.abs(pic.get(x, upper).getGreen() - pic.get(x, lower).getGreen());
        int deltaYB = Math.abs(pic.get(x, upper).getBlue() - pic.get(x, lower).getBlue());
        return deltaXB ^ 2 + deltaXG ^ 2 + deltaXR ^ 2 + deltaYR ^ 2 + deltaYG ^ 2 + deltaYB ^ 2;
    }

    public int[] findHorizontalSeam(){
        int[] res = new int[width()];
        double[][] minEnergy = new double[width()][height()];

        //calculate the sum of minimum energy.
        for(int i = 0; i < height(); i++){
            minEnergy[0][i] = energy(0, i);
        }
        for(int i = 1; i < width(); i++){
            for(int j = 0; j < height(); j++){
                if(j == 0){
                    minEnergy[i][j] = energy(i, j) + Math.min(minEnergy[i - 1][j], minEnergy[i - 1][j + 1]);
                }else if(j == height() - 1){
                    minEnergy[i][j] = energy(i, j) + Math.min(minEnergy[i - 1][j - 1], minEnergy[i - 1][j]);
                }else{
                    minEnergy[i][j] = energy(i, j) + Math.min(Math.min(minEnergy[i - 1][j - 1], minEnergy[i - 1][j]), minEnergy[i - 1][j + 1]);
                }
            }
        }

        //reverse the path
        //find the min total energy in last row
        double min = Double.MAX_VALUE;
        for(int i = 0; i < height(); i++){
            if(minEnergy[width() - 1][i] < min){
                min = minEnergy[width() - 1][i];
                res[res.length - 1] = i;
            }
        }

        for(int i = width() - 2; i >= 0; i--){
            if(res[i + 1] - 1 > 0){
                res[i] = res[i + 1] - 1;
                if(minEnergy[i][res[i] + 1] < minEnergy[i][res[i]]){
                    res[i] = res[i + 1];
                }
                if(res[i + 1] + 1 < height()){
                    if(minEnergy[i][res[i + 1] + 1] < minEnergy[i][res[i + 1]]){
                        res[i] = res[i + 1] + 1;
                    }
                }
            }else{
                res[i] = res[i + 1];
                if(res[i + 1] + 1 < height()){
                    if(minEnergy[i][res[i + 1] + 1] < minEnergy[i][res[i + 1]]){
                        res[i] = res[i + 1] + 1;
                    }
                }
            }
        }

        return res;
    }

    public int[] findVerticalSeam(){
        int[] res = new int[height()];
        double[][]  minEnergy = new double[width()][height()];

        //calculate the sum of minimum energy.
        for(int i = 0; i < width(); i++){
            minEnergy[i][0] = energy(i, 0);
        }
        for(int i = 1; i < height(); i++){
            for(int j = 0; j < width(); j++){
                if(j == 0){
                    minEnergy[j][i] = energy(j, i) + Math.min(minEnergy[j][i - 1], minEnergy[j + 1][i - 1]);
                }else if(j == width() - 1){
                    minEnergy[j][i] = energy(j, i) + Math.min(minEnergy[j - 1][i - 1], minEnergy[j][i - 1]);
                }else{
                    minEnergy[j][i] = energy(j, i) + Math.min(Math.min(minEnergy[j - 1][i - 1], minEnergy[j][i - 1]), minEnergy[j + 1][i - 1]);
                }
            }
        }

        //reverse the path
        //find the index of min total energy in last row.
        double min = Double.MAX_VALUE;
        for(int i = 0; i < width(); i++){
            if(minEnergy[i][height() - 1] < min){
                min = minEnergy[i][height() - 1];
                res[res.length - 1] = i;
            }
        }

        for(int i = height() - 2; i >= 0; i--){
            if(res[i + 1] - 1 > 0){
                res[i] = res[i + 1] - 1;
                if(minEnergy[res[i] + 1][i] < minEnergy[res[i]][i]){
                    res[i] = res[i + 1];
                }
                if(res[i + 1] + 1 < width()){
                    if(minEnergy[res[i + 1] + 1][i] < minEnergy[res[i]][i]){
                        res[i] = res[i + 1] + 1;
                    }
                }
            }else{
                res[i] = res[i + 1];

                if(res[i + 1] + 1 < width()){
                    if(minEnergy[res[i + 1] + 1][i] < minEnergy[res[i]][i]){
                        res[i] = res[i + 1] + 1;
                    }
                }
            }
        }
        return res;

    }

    public void removeHorizontalSeam(int[] seam){
        pic = SeamRemover.removeHorizontalSeam(pic, seam);
    }

    public void removeVerticalSeam(int[] seam){
        pic = SeamRemover.removeVerticalSeam(pic, seam);
    }
}
