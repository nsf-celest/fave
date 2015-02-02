/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.jme.util;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.logging.Logger;
// import java.lang.Math;

/**
 *
 * @author Erik brisson <ebrisson@bu.edu>
 */
public class GraphicsUtility {
    private static final Logger logger = Logger.getLogger(GraphicsUtility.class.getName());
    private Node rootNode;
    DirectionalLight sun;
    DirectionalLight light2;
    DirectionalLight light3;
    Vector3f vsun;
    Vector3f vlight2;
    Vector3f vlight3;
    AmbientLight alight;
    String mode;
    boolean rotWithCamera = false;
    public GraphicsUtility(Node rootNode) {
        this.rootNode = rootNode;
    }
    public void initLightsNode(Node lnode, String mode) {
        this.mode = mode;
        sun = new DirectionalLight();
        light2 = new DirectionalLight();
        light3 = new DirectionalLight();
        alight = new AmbientLight();
        
        if (mode.equals("orchard")) {     
            vsun = new Vector3f(-0.1f, -0.7f, -1.0f);
            vlight2 = new Vector3f(-1.0f, -0.1f, -1.0f);
            vlight3 = new Vector3f( 1.0f, -0.1f, -1.0f);
             
            sun.setDirection(vsun);
            sun.setColor(new ColorRGBA(0.6f, 0.6f, 0.59f, 1.0f));
            lnode.addLight(sun); 
           
            light2.setDirection(vlight2);
            ColorRGBA lcolor2 = new ColorRGBA(0.4f, 0.4f, 0.4f, 1.0f);
            light2.setColor(lcolor2);
            lnode.addLight(light2);

            light3.setDirection(vlight3);
            ColorRGBA lcolor3 = new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f);
            light3.setColor(lcolor3);
            lnode.addLight(light3);

            alight.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
            lnode.addLight(alight);
            
            rotWithCamera = true;
        }
        else if (mode.equals("play")) {              
            vsun = new Vector3f(-0.1f, -0.7f, -1.0f);
            sun.setDirection(vsun);
            sun.setColor(new ColorRGBA(0.6f, 0.6f, 0.6f, 1.0f));
            lnode.addLight(sun); 
  
            vlight2 = new Vector3f(-0.8f, -0.1f, 0.7f);      
            light2.setDirection(vlight2);
            ColorRGBA lcolor2 = new ColorRGBA(0.6f, 0.6f, 0.6f, 1f);
            light2.setColor(lcolor2);              
            lnode.addLight(light2);

            vlight3 = new Vector3f(0.8f, -0.1f, 0.6f);
            light3.setDirection(vlight3);
            ColorRGBA lcolor3 = new ColorRGBA(0.4f, 0.4f, 0.4f, 1f);
            light3.setColor(lcolor3);
            lnode.addLight(light3);

            alight.setColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f));
            lnode.addLight(alight);  
            
            rotWithCamera = true;
        }
        else if (mode.equals("office")) {              
            vsun = new Vector3f(-0.1f, -0.7f, -1.0f);
            sun.setDirection(vsun);
            sun.setColor(new ColorRGBA(0.6f, 0.6f, 0.6f, 1.0f));
            lnode.addLight(sun); 
  
            vlight2 = new Vector3f(-0.8f, -0.1f, 0.7f);      
            light2.setDirection(vlight2);
            ColorRGBA lcolor2 = new ColorRGBA(0.6f, 0.6f, 0.6f, 1f);
            light2.setColor(lcolor2);              
            lnode.addLight(light2);

            vlight3 = new Vector3f(0.8f, -0.1f, 0.6f);
            light3.setDirection(vlight3);
            ColorRGBA lcolor3 = new ColorRGBA(0.4f, 0.4f, 0.4f, 1f);
            light3.setColor(lcolor3);
            lnode.addLight(light3);

            alight.setColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f));
            lnode.addLight(alight);  
        }

    }
    private Vector3f vrot(Vector3f v, double c, double s) {
        return new Vector3f((float)(c*v.x+s*v.z), v.y, (float)(-s*v.x+c*v.z));
    }
    public void updateLights(Vector3f vdir) {
        if (rotWithCamera) {
            double a = Math.atan2((double)(-vdir.x), (double)(-vdir.z));
            double c = Math.cos(a);
            double s = Math.sin(a);
            if (mode.equals("orchard")) {
                sun.setDirection(vrot(vsun, c, s));
                light2.setDirection(vrot(vlight2, c, s));
                light3.setDirection(vrot(vlight3, c, s));
            }
            else if (mode.equals("play")) {
                sun.setDirection(vrot(vsun, c, s));
                light2.setDirection(vrot(vlight2, c, s));
                light3.setDirection(vrot(vlight3, c, s));
            }
        }
    }
    public int initLights(String mode) {
        initLightsNode(rootNode, mode);
        return 0;
    }
}
