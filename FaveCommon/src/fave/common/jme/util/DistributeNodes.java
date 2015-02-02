/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.jme.util;

import com.jme3.scene.Node;
import fave.common.model.makercore.ModelFactory;
import fave.common.model.makercore.VariModelMaker;
import fave.common.util.math.EulerFrame;
import fave.common.util.math.ProbDist;
import fave.common.util.math.SpatialDist;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author E12
 */
public class DistributeNodes {
    private static final Logger logger = Logger.getLogger(DistributeNodes.class.getName());
    /* begin "to delete" */
    
    SpatialDist sd; 
    ProbDist pdist;
    VariModelMaker maker;
    public DistributeNodes(SpatialDist sd, ProbDist pdist, VariModelMaker maker) {
        if (maker.ntypes() != pdist.size()) {
            logger.log(Level.SEVERE, "size mismatch"); 
        }
        this.maker = maker;
        this.sd = sd;
        this.pdist = pdist;
    }
    public Node make(String name) {
        Node d3d = new Node(name);
        for (int iobj=0; iobj<sd.nframes(); iobj++) {
            EulerFrame f = sd.frame(iobj);
            int index = pdist.nextIndex();
System.out.println("DistributeNodes.make: index = "+Integer.toString(index));
            Node n = maker.getModel(index);
            n.setLocalTranslation(f.origin().x, f.origin().y, f.origin().z);
            n.rotate(f.rot().x, f.rot().y, f.rot().z);
            d3d.attachChild(n);
        }
        return d3d;
    }
    
/* end "to delete" */
    public DistributeNodes() {
        
    }
    public void SetNodeFromEulerFrame(Node n, EulerFrame f) {
        n.setLocalTranslation(f.origin().x, f.origin().y, f.origin().z);
        n.rotate(f.rot().x, f.rot().y, f.rot().z);
    }
    public Node makeCluster(String name, SpatialDist sd, DistributeNodesCB fcb) {
        Node d3d = new Node(name);
        for (int iobj=0; iobj<sd.nframes(); iobj++) {
            EulerFrame f = sd.frame(iobj);
            Node n = fcb.frameCB(iobj);
            SetNodeFromEulerFrame(n, f);
            d3d.attachChild(n);
        }
        return d3d;
    }
    public Node makeCluster(String name, SpatialDist sd, ModelFactory fac) {
        Node d3d = new Node(name);
        for (int iobj=0; iobj<sd.nframes(); iobj++) {
            EulerFrame f = sd.frame(iobj);
            Node n = fac.nextModel();
            SetNodeFromEulerFrame(n, f);
            d3d.attachChild(n);
        }
        return d3d;
    }
}
