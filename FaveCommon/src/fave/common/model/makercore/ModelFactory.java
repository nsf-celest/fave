/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.model.makercore;

import com.jme3.scene.Node;
import fave.common.util.math.DetermDist;
import fave.common.util.math.ProbDist;
import java.util.Random;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class ModelFactory {
    VariModelMaker maker = null;
    ProbDist pdist = null;
    DetermDist ddist = null;
    int ngen = 0;
    Random Rgen;
    public ModelFactory(VariModelMaker maker, ProbDist pdist, long seed) {
         Rgen = new Random(seed);
         this.maker = maker;
         this.pdist = pdist;
    }
    public ModelFactory(VariModelMaker maker, DetermDist ddist, long seed) {
         Rgen = new Random(seed);
         this.maker = maker;
         this.ddist = ddist;
    }
    public Node nextModel() {
        if (pdist != null)
            return maker.getModel(pdist.nextIndex()); 
        else if (ddist != null)
            return maker.getModel(ddist.nextIndex()); 
        else
            return null;
    }
}
