/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package favecommon;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author E12
 */
public class Foo extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        BillboardControl billboard = new BillboardControl();
        Geometry healthbar = new Geometry("healthbar", new Quad(4f, 0.2f));
        Material mathb = mat.clone();
        mathb.setColor("Color", ColorRGBA.Red);
        healthbar.setMaterial(mathb);
        rootNode.attachChild(healthbar);
        healthbar.center();
        healthbar.move(0, 1, 2);
        healthbar.addControl(billboard);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}


