/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.model.simple;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import java.util.logging.Logger;

/**
 *
 * @author ebrisson
 */
public class SimpleColoredShapeMaker {
    private static final Logger logger = Logger.getLogger(SimpleColoredShapeMaker.class.getName());
    private AssetManager assetManager;
    public SimpleColoredShapeMaker(AssetManager manager){
        this.assetManager=manager;
    }

    public Geometry coloredBox(float xscale, float yscale, float zscale, ColorRGBA color) {
        Box box = new Box( Vector3f.ZERO, xscale, yscale, zscale);
        Geometry cuboid = new Geometry("cuboid", box);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        cuboid.setMaterial(mat);
        return cuboid;        
    }
    public Geometry coloredCube(float scale, ColorRGBA color) {
        Geometry g;
        g = coloredBox(scale, scale, scale, color);
        return g;
        // return coloredBox(scale, scale, scale, color);
    }
    public Geometry coloredSphere(float scale, ColorRGBA color) {
        // final int nlong = 9;
        // final int nlat = 5;
        final int nlong = 19;
        final int nlat = 19;
        Sphere smesh = new Sphere(nlat, nlong, scale);
        Geometry sphere = new Geometry("sphere", smesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        sphere.setMaterial(mat);
        return sphere;        
    }
    public Geometry coloredCylinder(float scale, ColorRGBA color) {
        final int axisSamples = 2;
        final int radialSamples = 9;
        final float radius = scale;
        final float height = 1.0f;
        final boolean closed = true;
        final boolean inverted = false;
        Cylinder cmesh = new Cylinder(axisSamples, radialSamples, radius, height, closed, inverted);
        Geometry cylinder = new Geometry("cylinder", cmesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        cylinder.setMaterial(mat);
        return cylinder;        
    }
    public Node coloredAxes(float scale) {
        Geometry o = coloredCube(0.02f, ColorRGBA.Gray);
        Geometry x = coloredCube(0.02f, ColorRGBA.Red);
        x.move(1.0f, 0.0f, 0.0f);
        Geometry y = coloredCube(0.02f, ColorRGBA.Green);
        y.move(0.0f, 1.0f, 0.0f);
        Geometry z = coloredCube(0.02f, ColorRGBA.Blue);
        z.move(0.0f, 0.0f, 1.0f);
        Node origin = new Node("origin");
        origin.attachChild(o);
        origin.attachChild(x);
        origin.attachChild(y);
        origin.attachChild(z);
        origin.scale(scale);
        return origin;
    }
    public Node coloredAxes() {
        return coloredAxes(1.0f); 
    }
}
