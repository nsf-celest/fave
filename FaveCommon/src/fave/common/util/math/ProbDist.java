/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.util.math;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pass in an array of n floats representing probabilities of n events
 * They will be normalized, i.e., they do NOT need to add to one, and the distribution computed.
 * Then the caller can ask for one chosen from a uniform dist, or can pass in a number in [0,1] 
 * and the corresponding index from the dist will be returned.
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class ProbDist {
    private static final Logger logger = Logger.getLogger(ProbDist.class.getName());
    private Random Rgen;
    private float pdist[];
boolean used = false;
    public ProbDist(int n, long seed) { /* this form just makes uniform dist */
         Rgen = new Random(seed);
         float[] probs = new float[n];
         for (int i=0; i<n; i++) {
             probs[i] = 1f/n;
         }
         makePdist(probs);
    }
    public ProbDist(float[] probs, long seed) {
         Rgen = new Random(seed);
         makePdist(probs);
    }
    private void makePdist(float[] probs) {
        int n = probs.length;
        pdist = new float[n];
        pdist[0] = probs[0];
        for (int i=1; i<n; i++) {
             pdist[i] = pdist[i-1] + probs[i]; 
        }
        float tot = pdist[n-1];
        for (int i=0; i<n; i++) {
             pdist[i] /= tot;
logger.log(Level.INFO, "i, n, prob, dist = {0}, {1}, {2}, {3}",
new Object[]{i, n, probs[i], pdist[i]});
        }
    }
    public int nextIndex() {
        return indexFromRV(Rgen.nextFloat());
    }
    public int indexFromRV(float x) {
        int index = 0;
        while (x>pdist[index]) {
            if (index==pdist.length-1) {
                break;
            }
            index++;
        }
if (!used) { 
for (int i=0; i<pdist.length; i++) {
logger.log(Level.INFO, "pdist[{0}] = {1}", new Object[]{i, pdist[i]});
}
logger.log(Level.INFO, "x, index, n = {0}, {1}, {2}", new Object[]{x, index, pdist.length});
used = true;
}
/*
String s = "";
for (int i=0; i<pdist.length; i++) {
s += " p["+i+"]="+pdist[i];
}
s+="  x, index = "+x+" "+index;
System.out.println(s);
*/
        return index;
    }
    public int size() {
        return pdist.length;
    }
    public void dumpProb(String mess) {
        for (int i=0; i<pdist.length; i++) {
            System.out.println(mess+" pdist["+i+"] = "+pdist[i]);
        }
    }
}
