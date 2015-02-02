/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * 
 * This class mimics a subset of Random functionality,
 * but requires a non-zero seed (which an application may
 * get from RandomMgr).
 */
package fave.common.util.math;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class RandomGen {
    private static final Logger logger = Logger.getLogger(RandomGen.class.getName());
    Random Rgen;
    long seed;
    public RandomGen(long seed) {
        if (seed == 0) {
            logger.log(Level.SEVERE, "Non-zero seed required to init RandomGen");
        }
        Rgen = new Random(seed);
        this.seed = seed;
    }
    public long getSeed() {
        return seed;
    }
    public long nextLong() {
        return Rgen.nextLong();
    }
    public float nextFloat() {
        return Rgen.nextFloat();
    }
}
