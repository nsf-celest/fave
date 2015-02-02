/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.model.vent;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author E12
 */
public class PatternVG implements VentGenerator.Maker { 
    String type = null;
    float scale = 1.0f;
    VentGenerator.Maker childMethod = null;
    Vector3f min, max;
    int nx, ny, nz;
    
    public PatternVG() {
    }
    
    public PatternVG(String type, float scale, VentGenerator.Maker childMethod) {
        this.type = type;
        this.scale = scale;
        this.childMethod = childMethod;
    }
    
    public void setChildMethod(VentGenerator.Maker maker) {
        this.childMethod = maker;
    }
    
    public void initGrid(float xmin, float xmax, int nx,
                         float ymin, float ymax, int ny,
                         float zmin, float zmax, int nz) {
        type = "grid3d";
        min = new Vector3f(xmin, ymin, zmin);
        max = new Vector3f(xmax, ymax, zmax);
        this.nx = nx;  this.ny = ny;  this.nz = nz;
    }   
    
    public Spatial make(VentGenerator.UserData data) {
        if (type.equals("cube-faces")) {
             Node nresult = new Node();
             for (int i=0; i<6; i++) {
                 Node n = new Node();
                 switch (i) {
                     case 0: n.move(-scale,0f,0f); break;
                     case 1: n.move( scale,0f,0f); break;
                     case 2: n.move(0f,-scale,0f); break;
                     case 3: n.move(0f, scale,0f); break;
                     case 4: n.move(0f,0f,-scale); break;
                     case 5: n.move(0f,0f, scale); break;
                 }
                 nresult.attachChild(n);
                 n.attachChild(childMethod.make(null));
             }
             return nresult;
        }
        else if (type.equals("grid3d")) {
            Node nresult = new Node();
            float ax, ay, az, x, y, z;
            for (int ix=0; ix<nx; ix++) {
                if (nx > 1) {
                    ax = (float)ix / (float)(nx-1);
                } else {
                    ax = 0.5f;
                }
                x = (1f-ax)*min.x + ax*max.x;
                for (int iy=0; iy<ny; iy++) {
                    if (ny > 1) {
                        ay = (float)iy / (float)(ny-1);
                    } else {
                        ay = 0.5f;
                    }
                    y = (1f-ay)*min.y + ay*max.y;
                    for (int iz=0; iz<nz; iz++) {
                        if (nz > 1) {
                            az = (float)iz / (float)(nz-1);
                        } else {
                            az = 0.5f;
                        }
                        z = (1f-az)*min.z + az*max.z; 
                        Node n = new Node();
                        n.move(x, y, z);
                        nresult.attachChild(n);
                        n.attachChild(childMethod.make(null));
                    }
                }
            }
            return nresult;
        }
        else {
            return null;
        }
    }
}
