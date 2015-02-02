/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * This class exists to ensure that there is at most one instance
 * of a Random number generated "non-deterministically".  RandomMgr
 * exists as a singleton "reference" source.
 * 
 * The companion class, RandomGen, which may be used at any level
 * of granularity, always requires a non-zero seed.
 */
package fave.common.util.math;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class RandomMgr {
    private static final Logger logger = Logger.getLogger(RandomMgr.class.getName());
    static Random Rgen;
    static long seed = 0;
    static public void init(long seed) {
        if (RandomMgr.seed == 0) {
            if (seed == 0) {
                Random RgenSystem = new Random();
                seed = RgenSystem.nextLong();
            }
            RandomMgr.seed = seed;
            Rgen = new Random(seed);
// System.out.println("RandomMgr seed = " + seed);
        }
        else {
            logger.log(Level.SEVERE, "Multiple attempts to init RandomMgr");
        }
    }
    static public long getSeed() {
        return seed;
    }
    static public long nextLong() {
        if (RandomMgr.seed == 0) {
            logger.log(Level.SEVERE, "Called before init");
        }
        return Rgen.nextLong();
    }
    static public float nextFloat() {
        if (RandomMgr.seed == 0) {
            logger.log(Level.SEVERE, "Called before init");
        }
        return Rgen.nextFloat();
    }
}
