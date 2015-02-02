/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.model.vent;

import com.jme3.scene.Spatial;


public class LeafVG implements VentGenerator.Maker {
    Spatial s;
    public LeafVG(Spatial s) {
        this.s = s;
    }
    public Spatial make(VentGenerator.UserData data) {
        if (s != null) {
            return s.clone();
        }
        else {
            return null;
        }
    }
}