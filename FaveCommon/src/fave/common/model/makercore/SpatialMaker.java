/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fave.common.model.makercore;

import com.jme3.scene.Spatial;

/**
 *
 * @author E12
 */
public class SpatialMaker {
    Object data = null;
    public SpatialMaker() {
    }
    public SpatialMaker(Object data) {
        this.data = data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Spatial cb() {
        return null;
    }
}
