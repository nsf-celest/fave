/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.model;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import fave.common.model.simple.SimpleShapeMaker;
import java.util.logging.Logger;
import fave.common.model.makercore.VariModelMaker;
import fave.common.util.math.GeomUtility;

/**
 *
 * @author Erik Brisson
 */
public class CrossMaker extends VariModelMaker {
    private static final Logger logger = Logger.getLogger(CrossMaker.class.getName());
    static int count = 0;
    public CrossMaker(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    public int ntypes() {
       return 3;
    }
    public Node getModel(int ivar) {
            final float length = 0.5f;
            final float width  = 0.07f;
            final float depth  = 0.07f;
            final ColorRGBA barcolor[] = {new ColorRGBA(0.0f, 0.5f, 0.7f, 1.0f),
                                      new ColorRGBA(0.7f, 0.0f, 0.5f, 1.0f), 
                                      new ColorRGBA(0.7f, 0.5f, 0.0f, 1.0f)};
            final int perm[][] = { {0,1,2}, {0,2,1}, {1,0,2}, {1,2,0}, {2,0,1}, {2,1,0} };
            Node n;
            Geometry g;
            float yshift;
            int iperm;
            switch (ivar) {
            case 0:
            default:
                iperm = 0;
                yshift = 0f;
                break;
            case 1:
                iperm = 3;
                yshift = 0.10f;
                break;
            case 2:                    
                iperm = 4;
                yshift = -0.10f;
                break;
            }
            String name = "jack_" + count;
            count++;
            n = new Node(name);

            SimpleShapeMaker shapeMaker = new SimpleShapeMaker(assetManager);
            
            shapeMaker.setDefaults();
            shapeMaker.setLit(true);
            shapeMaker.setScale(length, width, depth);
            
            Box box = new Box(Vector3f.ZERO, 1f, 1f, 1f);
            shapeMaker.setColor(barcolor[perm[iperm][0]]);
            n.attachChild(shapeMaker.apply(box, "bar0"));
            
            box = new Box( Vector3f.ZERO, 1f, 1f, 1f);
            shapeMaker.setColor(barcolor[perm[iperm][1]]);
            shapeMaker.setVorient(new Vector3f(1f, 0f, 0f));
            shapeMaker.setOffset(0f, yshift, 0f);
            n.attachChild(shapeMaker.apply(box, "bar1"));

            box = new Box( Vector3f.ZERO, 1f, 1f, 1f);
            shapeMaker.setColor(barcolor[perm[iperm][2]]);
            shapeMaker.setRotation(0f, GeomUtility.RPD*90f, 0f);
            shapeMaker.setOffset(0f, 0f, 0f);
            n.attachChild(shapeMaker.apply(box, "bar2"));

        return n;
    }
}
