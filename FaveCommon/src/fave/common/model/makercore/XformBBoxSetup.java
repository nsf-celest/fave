/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.common.model.makercore;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Developed as part of the Celeste FLAME project, 2013
 * 
 * This class provides a way to set up scaling, translation, and rotation
 * with a specified pivot point, allowing placement of the models with
 * accurate alignment
 * The pivot is specified by fractional location in the bounding box
 * of the model.
 * Rotation is about the pivot point.
 * Translation moves the pivot point to the specified destination.
 * 
 * XformNodeSetup sets transformation values in the node that it returns, so to move it
 * again either compose with current transformation or put under a new node
 */


/**
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class XformBBoxSetup extends XformSetup {
    private static final Logger logger = Logger.getLogger(fave.common.model.makercore.XformBBoxSetup.class.getName());
    public enum Xloc {LEFT, MIDDLE, RIGHT};
    public enum Yloc {BOTTOM, MIDDLE, TOP};
    public enum Zloc {BACK, MIDDLE, FRONT};
    enum ScaleToType {NONE, XSIZE, YSIZE, ZSIZE, DIAG, LONGEST, NONUNIFXYZ};
    ScaleToType doScaleTo = ScaleToType.NONE;
    float xScaleTo, yScaleTo, zScaleTo, scaleTo;
    BoundingBox bbox;
    float xfracPivot = 0.5f;
    float yfracPivot = 0.5f;
    float zfracPivot = 0.5f;
    boolean explicitPivotSet = false;
    public XformBBoxSetup() {
         super();
    }
    public XformBBoxSetup(BoundingBox bbox) {
        super();
        if (bbox == null)
            logger.log(Level.SEVERE, "bbox == null");
        this.bbox = bbox;
    }
    public void setBBox(BoundingBox bbox) {
        this.bbox = bbox;
        internalRescale();
    }
    void internalRescale() {
        switch (doScaleTo) {
            case XSIZE:
                setScaleXTo(xScaleTo);
                break;
            case YSIZE:
                setScaleYTo(yScaleTo);
                break;
            case ZSIZE:
                setScaleZTo(zScaleTo);
                break;
            case DIAG:
                setScaleDiagTo(scaleTo);
                break;
            case LONGEST:
                setScaleLongestTo(scaleTo);
                break;
            case NONUNIFXYZ:
                setScaleNonUniformTo(xScaleTo, yScaleTo, zScaleTo);
                break;
            case NONE:
            default:
                break;
        }
    }
    public void setScaleXTo(float xsize) {
        if (bbox!=null && bbox.getXExtent()!=0)
            internalSetUniformScale(xsize / (2*bbox.getXExtent()));
        doScaleTo = ScaleToType.XSIZE;
        xScaleTo = xsize;
    }
    public void setScaleYTo(float ysize) {
        if (bbox!=null && bbox.getYExtent()!=0)
            internalSetUniformScale(ysize / (2*bbox.getYExtent()));
        doScaleTo = ScaleToType.YSIZE;
        yScaleTo = ysize;
    }
    public void setScaleZTo(float zsize) {
        if (bbox!=null && bbox.getZExtent()!=0)
            internalSetUniformScale(zsize / (2*bbox.getZExtent()));
        doScaleTo = ScaleToType.ZSIZE;
        zScaleTo = zsize;
    }
    public void setScaleDiagTo(float diagsize) {
        Vector3f vdiag = new Vector3f(bbox.getXExtent(), bbox.getYExtent(), bbox.getZExtent());
        if (vdiag.length() == 0)
            logger.log(Level.WARNING, "vdiag.length() == 0");
        else
            internalSetUniformScale(diagsize / (2*vdiag.length()));
        doScaleTo = ScaleToType.DIAG;
        scaleTo = diagsize;
    }
    public void setScaleLongestTo(float longestsize) {
        float maxextent = Math.max(bbox.getXExtent(), bbox.getYExtent());
        maxextent = Math.max(maxextent, bbox.getZExtent());
        if (maxextent == 0)
            logger.log(Level.WARNING, "maxextent == 0");
        else
            internalSetUniformScale(longestsize / (2*maxextent));
        doScaleTo = ScaleToType.LONGEST;
        scaleTo = longestsize;
    }
    public void setScaleNonUniformTo(float xsize, float ysize, float zsize) {
        if (bbox.getXExtent() == 0) {
            logger.log(Level.WARNING, "bbox.getXExtent == 0");
            xscale = 0f;
        }
        else
            xscale = (xsize / (2*bbox.getXExtent()));
        if (bbox.getYExtent() == 0) {
            logger.log(Level.WARNING, "bbox.getYExtent == 0");
            yscale = 0f;
        }
        else
            yscale = (ysize / (2*bbox.getYExtent()));
        if (bbox.getZExtent() == 0) {
            logger.log(Level.WARNING, "bbox.getZExtent == 0");
            zscale = 0f;
        }
        else
            zscale = (zsize / (2*bbox.getZExtent()));
        doScaleTo = ScaleToType.NONUNIFXYZ;
        xScaleTo = xsize;
        yScaleTo = ysize;
        zScaleTo = zsize;
    }
    void internalSetUniformScale(float scale) {
        this.xscale = scale;
        this.yscale = scale;
        this.zscale = scale;
    }
    public float getScaledXsize() {
        return xscale*2*bbox.getXExtent();
    }
    public float getScaledYsize() {
        return yscale*2*bbox.getYExtent();
    }
    public float getScaledZsize() {
        return zscale*2*bbox.getZExtent();
    }
    
    public void setPivot(Xloc xloc, Yloc yloc, Zloc zloc) {
        float xfrac, yfrac, zfrac;
        switch (xloc) {
            case LEFT:   xfrac = 0.0f; break;
            case MIDDLE: xfrac = 0.5f; break;
            case RIGHT:  xfrac = 1.0f; break;
            default: xfrac = 0.5f; break;
        }
        switch (yloc) {
            case BOTTOM: yfrac = 0.0f; break;
            case MIDDLE: yfrac = 0.5f; break;
            case TOP:    yfrac = 1.0f; break;
            default: yfrac = 0.5f; break;
        }
        switch (zloc) {
            case BACK:   zfrac = 0.0f; break;
            case MIDDLE: zfrac = 0.5f; break;
            case FRONT:  zfrac = 1.0f; break;
            default: zfrac = 0.5f; break;
        }
        setPivotFractional(xfrac, yfrac, zfrac);
    }
    public void setPivotFractional(float xfrac, float yfrac, float zfrac) {
        xfracPivot = xfrac;
        yfracPivot = yfrac;
        zfracPivot = zfrac;
        unSetPivot();
    }
    @Override public void setPivot(Vector3f vpivot)
    {
        explicitPivotSet = true;
        this.vpivot = vpivot;
    }
    public void unSetPivot() {
        explicitPivotSet = false;
    }
    @Override
    public Vector3f getPivot() {
        if (explicitPivotSet)
            return vpivot;
        Vector3f vpivotCompute = new Vector3f();
        Vector3f vmin = new Vector3f();
        Vector3f vmax = new Vector3f();
        vmin = bbox.getMin(vmin);
        vmax = bbox.getMax(vmax);
        vpivotCompute.set((1f-xfracPivot)*vmin.x + xfracPivot*vmax.x, (1f-yfracPivot)*vmin.y + yfracPivot*vmax.y,
                   (1f-zfracPivot)*vmin.z + zfracPivot*vmax.z);
        return vpivotCompute;
    }
}
