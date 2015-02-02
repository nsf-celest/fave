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
import com.jme3.scene.Mesh;
import java.util.logging.Logger;
import fave.common.util.math.EulerFrame;

/**
 *
 * @author ebrisson
 * Simple 3d objects:
 *     given a basic object, want to
 *         scale
 *         specify whether local origin is at center or base
 *         reorient along a given vector
 *         apply a colored material, lit or unlit
 *     vorient specifies the direction of the major axis
 *     ocenter true implies the object is centered on the origin
 *         if not, it "sits on" the origin
 
 *     Material generated here
 *         boolean lit determines if material responds to lights

 */
public class SimpleShapeMaker {
    private static final Logger logger = Logger.getLogger(SimpleShapeMaker.class.getName());
    AssetManager assetManager;
    float xscale, yscale, zscale;
    float xoff, yoff, zoff;
    float xrot, yrot, zrot;
    EulerFrame frame;
    boolean lit;
    boolean ocenter;
    Vector3f vorient;
    boolean dorot;
    boolean doorient;
    boolean doframe;
    ColorRGBA color;
    public SimpleShapeMaker(AssetManager manager){
        this.assetManager=manager;
        setDefaults();
    }
    public final void setDefaults() {
        xscale = yscale = zscale = 1f;
        xoff = yoff = zoff = 0f;
        color = ColorRGBA.Gray;
        lit = false;
        ocenter = true;
        vorient = Vector3f.UNIT_Y;
        dorot = false;
        doorient = false;
        doframe = false;
    }
    public void setScale(float xscale, float yscale, float zscale) {
        this.xscale = xscale;
        this.yscale = yscale;
        this.zscale = zscale;
    }
    public void setOffset(float xoff, float yoff, float zoff) {
        this.xoff = xoff;
        this.yoff = yoff;
        this.zoff = zoff;
    }
    public void setRotation(float xrot, float yrot, float zrot) {
        this.xrot = xrot;
        this.yrot = yrot;
        this.zrot = zrot;
        dorot = true;
        doorient = false;
    }
    public void setColor(ColorRGBA color) {
        this.color = color;
    }
    public void setLit(boolean lit) {
        this.lit = lit;
    }
    public void setOcenter(boolean ocenter) {
        this.ocenter = ocenter;
    }
    public void setVorient(Vector3f vorient) {
        this.vorient = vorient;
        doorient = true;
        dorot = false;
    }
    public void setFrame(EulerFrame frame) {
        this.frame = frame;
    }
    public Geometry apply(Mesh mesh, String name) {
        Geometry geom = new Geometry(name, mesh);
        Material mat;
        if (lit) {
            mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setBoolean("UseMaterialColors",true);    
            mat.setColor("Specular", color);
            mat.setColor("Diffuse", color);
            mat.setFloat("Shininess", 3f);    
            mat.setColor("Ambient", color);
        }
        else {
            mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", color);
        }
        geom.setMaterial(mat);
        geom.setLocalScale(xscale, yscale, zscale);
        if (dorot) {
            geom.rotate(xrot, yrot, zrot);
        }
        else if (doorient) {
            geom.rotateUpTo(vorient);
        }
        if (ocenter) {
            geom.setLocalTranslation(xoff, yoff, zoff);
        }
        else {
            geom.setLocalTranslation(xoff, yoff+yscale/2f, zoff);       
        }
        if (doframe) {
            Vector3f vtrans = frame.origin();
            Vector3f vrot = frame.rot();
            geom.move(vtrans.x, vtrans.y, vtrans.z);
            geom.rotate(vrot.x, vrot.y, vrot.z);
        }
        return geom;
    }
}
