package hw4.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO: Write a utility function that returns true if the given oomages 
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] label = new int[M];
        for(Oomage so : oomages){
            label[((so).hashCode() & 0x7FFFFFF) % M] += 1;
        }
        int N = oomages.size();
        for(int i = 0; i < M; i++){
            if(label[i] > N / 2.5) return false;
            if(label[i] < N / 50) return false;
        }
        return true;
    }
}
