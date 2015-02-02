/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * This represents a deterministic distribution on n items
 * The input nOfInd array specifies how many of each index [0 .. n-1] are in the set to draw from
 * Calls to nextInt return an index in [0 .. n-1]
 * so that calls will get nOfInd[0] 0's, nOfInd[1] 1's, etc
 * Will be drawn randomly
 */
package fave.common.util.math;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class DetermDist {
    private static final Logger logger = Logger.getLogger(DetermDist.class.getName());
    int nindex;
    int dist[];
    int navail;
    int ntot;
    Random Rgen;
    public DetermDist(int nindex, int nOfInd[], long seed) {
        // Check that nindex <= size of nOfInd
        Rgen = new Random(seed);
        this.nindex = nindex;
        ntot = 0;
        for (int i=0; i<nindex; i++) {
            ntot += nOfInd[i];
        }
        dist = new int[ntot];
        int k = 0;
        for (int i=0; i<nindex; i++) {
            for (int j=0; j<nOfInd[i]; j++) {
                dist[k++] = i;
            }
        }    
        reset();
    }
    final public void reset() {
        navail = ntot;        
    }
    public int nextIndex() {
        if (navail > 0) {
            int i = (int)(navail*Rgen.nextDouble());
            int draw = dist[i];
            dist[i] = dist[navail-1];
            dist[navail-1] = draw;
            navail--;
            return draw;
        }
        else { // put in a reasonable error result
            return -1;
        }
    }
}
