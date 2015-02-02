/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.jme.util;

import com.jme3.scene.Node;
import fave.common.util.math.EulerFrame;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public interface DistributeNodesCB {
    public Node frameCB(int i, EulerFrame f);
    public Node frameCB(int i);
}
