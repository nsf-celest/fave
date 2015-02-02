/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.common.sg;

import com.jme3.math.Vector3f;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public interface ISceneGraphCB {
    public void nodePicked(String nodeName, Vector3f ptHit, float dHit);
    public void camPosLookPoint(String nodeName, Vector3f vpos, Vector3f vlookat, Vector3f vup);
    public void camPosStareDir(String nodeName, Vector3f vpos, Vector3f vstaredir, Vector3f vup);
}
