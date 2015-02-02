package fave.common.util.math;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Provides distributions inside of 3d boxes and sphere and 2d disks and rectangles.
 * The rect bbox that frames are chosen from is inherited from BoundingBox.  The user
 * can set these using methods from there.  The set methods here control the restriction
 * of the chosen frames and/or projections into a lower dimensional space.
 * The caller can also set a constraint on the minimu allowed separation between frames
 * in either the L1 (taxicab) or L2 (euclidean) distance metrics.
 * The assign routines return 0 on no error
 *                     return 1 if there is an error
 * 
 * Adding a second kind of frame set.  It is extensible - can add as many frames
 * as desired, at any time.  The set has one metric.  When adding a new frame, a
 * weight (distance) is specified at call time.  If the new frame is "far enough" from
 * all other (weighted) frames, it is added, and success is returned.  Else it
 * is not added, and failure is returned.  A request to add a frame randomly satisfying
 * the criteria can also be made.  NOT YET IMPLEMENTED.
 * 
 * Adding the option of setting frames explicitly.  It is expected that this will be done
 * before the random frames are chosen.  It is recommended that these by added as
 * iframe 0, 1, 2, ...
 * There is no check at this time that they do not overlap.
 */
public class SpatialDist extends SpatialBoundingBox {
    private static final Logger logger = Logger.getLogger(SpatialDist.class.getName());
    static int LocalErrorNone = 0;
    static int LocalErrorGeneric = 1;
    int neframes;
    EulerFrame[] eframes;
    boolean[] hardFixed;
    Vector3f sphereCenter;
    float radius;
    public enum DistType {UNSET, BOX3D, SPHERE3D};
    SpatialDist.DistType dtype;

    ArrayList<EulerFrame> frameList = null;
    
    enum SeparationCriterion {NONE, L1, L2};
    SpatialDist.SeparationCriterion sepCrit;
    float sepL2;
    Vector3f sepL1;
    
    enum AvoidanceCriterion {NONE, L1, L2};
    /*
    AvoidanceCriterion avoidCrit;
    float avoidL2;
    Vector3f avoidL1;
    Vector3f avoidVpos;
    */
    
    class Avoid {
        SpatialDist.AvoidanceCriterion avoidCrit = SpatialDist.AvoidanceCriterion.NONE;
        float avoidL2;
        Vector3f avoidL1;
        Vector3f avoidVpos;
    }
    ArrayList<SpatialDist.Avoid> avoidList = new ArrayList<SpatialDist.Avoid>();
    
    public enum Metric {NONE, L1, L2, Linf};
    
    // public enum Plane2d {XY, XZ, YZ};
    // Plane2d planeOrientation;
    // static Random R = new Random(1562417L);
    RandomGen R = null;
    final float RPD = ((float)Math.PI / 180);
    boolean extensible;
    public SpatialDist(int nf, long seed) {
        super();
        R = new RandomGen(seed);
        extensible = false;
        neframes = nf;
        eframes = new EulerFrame[neframes];
        hardFixed = new boolean[neframes];
        for (int i=0; i<neframes; i++) {
            eframes[i] = new EulerFrame();
            hardFixed[i] = false;
        }
        dtype = SpatialDist.DistType.UNSET;
        sepCrit = SpatialDist.SeparationCriterion.NONE;
        // avoidCrit = AvoidanceCriterion.NONE;
    }
    public SpatialDist(SpatialDist.Metric M, long seed) {
        super();
        R = new RandomGen(seed);
        extensible = true;
        frameList = new ArrayList<EulerFrame>();
    }
    public int nframes() {
        if (extensible)
            return frameList.size();
        else 
            return neframes; 
    }
    public EulerFrame frame(int i) {
        if (extensible)
            return frameList.get(i);
        else 
            return eframes[i];
    }
    public void initBox3d() {
        dtype = SpatialDist.DistType.BOX3D;
    }
    public void initSphere3d(Vector3f vcenter, float radius) {
        sphereCenter = new Vector3f(vcenter);
        this.radius = radius;
        dtype = SpatialDist.DistType.SPHERE3D;
    }
    public void initSphere3d(float xcenter, float ycenter, float zcenter, float radius) {
        sphereCenter = new Vector3f(xcenter, ycenter, zcenter);
        this.radius = radius;
        dtype = SpatialDist.DistType.SPHERE3D;
    }
    /*
    public void initDisk2d(Plane2d planeOrientation, Vector2f vcenter, float radius) {
        this.planeOrientation = planeOrientation;
        diskCenter = new Vector2f(vcenter);
        this.radius = radius;
        dtype = DistType.DISK2D;
    }
    */
    public void setSeparationNone() {
        sepCrit = SpatialDist.SeparationCriterion.NONE;
    }
    public void setSeparationL1(Vector3f sepL1) {
        this.sepL1 = new Vector3f(sepL1);
        sepCrit = SpatialDist.SeparationCriterion.L1;
    }
    public void setSeparationL2(float sepL2) {
        this.sepL2 = sepL2;
        sepCrit = SpatialDist.SeparationCriterion.L2;
    }
    // Routined to specify avoidance of one point
    public void setAvoidanceNone() {
        avoidList = null;
        // avoidCrit = AvoidanceCriterion.NONE;
    }
    public void addAvoidanceL1(Vector3f vpos, Vector3f sepL1) {
// System.out.println("SpatialDist:addAvoidanceL1: vpos, sepL1 = "+vpos+" "+sepL1);
        SpatialDist.Avoid a = new SpatialDist.Avoid();
        a.avoidVpos = new Vector3f(vpos);
        a.avoidL1 = new Vector3f(sepL1);
        a.avoidCrit = SpatialDist.AvoidanceCriterion.L1;
        avoidList.add(a);
    }
    public void addAvoidanceL2(Vector3f vpos, float sepL2) {
// System.out.println("SpatialDist:addAvoidanceL2: vpos, sepL2 = "+vpos+" "+sepL2);
        SpatialDist.Avoid a = new SpatialDist.Avoid();
        a.avoidVpos = new Vector3f(vpos);
        a.avoidL2 = sepL2;
        a.avoidCrit = SpatialDist.AvoidanceCriterion.L2;
        avoidList.add(a);
    }
    public void hardPlacePosnRot(int iframe, float x, float y, float z, float xr, float yr, float zr) {
        eframes[iframe].setOrigin(x, y, z);
        eframes[iframe].setRot(xr, yr, zr);
        hardFixed[iframe] = true;
    }
    // Below are the actual "make" routines
    public int assignRandomUniformPosn() {
        int localError = dtypeCheck();
        if (localError == LocalErrorNone) {
            for (int i=0; i<neframes; i++) {
// System.out.println("SpatialDist.assignRandomUnifPosn: i, hardFixed = "+Integer.toString(i)+" "+hardFixed[i]);
                if (!hardFixed[i]) {
                    do {
                        float x = xmin() + R.nextFloat() * (xmax() - xmin());
                        float y = ymin() + R.nextFloat() * (ymax() - ymin());
                        float z = zmin() + R.nextFloat() * (zmax() - zmin());
                        eframes[i].setOrigin(x, y, z);
                    } while (!posnOkay(eframes[i]) 
                             || !separationOkay(i, eframes[i]) || !avoidanceOkay(eframes[i]));
                }
// eframes[i].dump("frame["+Integer.toString(i)+"]");
            }
        }
        return localError;
    }
    public int assignRandomUniformRot() {
        int localError = dtypeCheck();
        if (localError == LocalErrorNone)
            localError = assignRandomUniformRot(360f, 360f, 360f);
        return localError;
    }
    public int assignRandomUniformRot(float xrange, float yrange, float zrange) {
        int localError = dtypeCheck();
        if (localError == LocalErrorNone) {
            for (int i=0; i<neframes; i++) {
                if (!hardFixed[i]) {
                    float xr = RPD * xrange*R.nextFloat();
                    float yr = RPD * yrange*R.nextFloat();
                    float zr = RPD * zrange*R.nextFloat();
                    eframes[i].setRot(xr, yr, zr);
                }
            }
        }
        return localError;
    }
    public int assignRandomUniformPosnRot() {
        int localError = dtypeCheck();
        if (localError == LocalErrorNone)
            localError = assignRandomUniformPosn();
        if (localError == LocalErrorNone)
            localError = assignRandomUniformRot();
        return localError;
    }
    private int dtypeCheck() {
        if (dtype == SpatialDist.DistType.UNSET) {
            logger.log(Level.SEVERE, "dtype unset");
            return LocalErrorGeneric;
        }
        return LocalErrorNone;
    }
    /*
    private void projectToCenterPlane(EulerFrame f) {
        if (dtype==DistType.RECT2D || dtype==DistType.DISK2D) {
            Vector3f forigin = f.origin();        
            switch (planeOrientation) {
                case XY:
                    f.setOrigin(forigin.x, forigin.y, 0f);
                    break;
                case XZ:
                    f.setOrigin(forigin.x, 0f, forigin.z);
                    break;
                case YZ:
                    f.setOrigin(0, forigin.y, forigin.z);
                    break;
            }
        }
    }
    */
    /**
     * The main dist pass just chooses randomly in a rect
     * This test checks that all of the frames lie within the subvolume desired
     * @param f
     * @return
     */
    private boolean posnOkay(EulerFrame f) {
        boolean okay = true;
        if (dtype==SpatialDist.DistType.SPHERE3D) {
            Vector3f v = new Vector3f(f.origin().x, f.origin().y, f.origin().z);
            v.subtract(vcenter()); // v = f.origin - sphcen
            if (v.lengthSquared() > radius*radius) {
                okay = false;
            }
        }
        return okay;
    }

    /**
     * this checks (if one is specified) if minimum separation requirement met
     * @param f
     * @return
     */
    public boolean separationOkay(int icur, EulerFrame fcur) {
        boolean okay = true;
        if (sepCrit != SpatialDist.SeparationCriterion.NONE) {
            Vector3f pcur = new Vector3f(fcur.origin());
            for (int i=0; i<neframes; i++) {
                if (i<icur || (i>icur && hardFixed[i])) {
            // for (int i=0; i<icur; i++) {
                    Vector3f p = eframes[i].origin();
                    Vector3f d = (new Vector3f(p)).subtract(pcur);
                    if (   (sepCrit == SpatialDist.SeparationCriterion.L1 &&
                            (Math.abs(d.x)<=sepL1.x && Math.abs(d.y)<=sepL1.y && Math.abs(d.z)<=sepL1.z))
                        || (sepCrit == SpatialDist.SeparationCriterion.L2 && (d.length()<=sepL2))) {
                            okay = false;
                            break;
                   }
                }
            }
        }
        return okay;
    }
        /**
     * this checks (if one is specified) if minimum separation requirment met
     * @param f
     * @return
     */
    public boolean avoidanceOkay(EulerFrame fcur) {
        boolean okay = true;
        if (avoidList != null) {
          for (int i=0; i<avoidList.size(); i++) {
            SpatialDist.Avoid a = avoidList.get(i);
            if (a.avoidCrit != SpatialDist.AvoidanceCriterion.NONE) {
                Vector3f pcur = new Vector3f(fcur.origin());
                Vector3f d = a.avoidVpos.subtract(pcur);
                if (   (a.avoidCrit == SpatialDist.AvoidanceCriterion.L1 &&
                        (Math.abs(d.x)<=a.avoidL1.x && Math.abs(d.y)<=a.avoidL1.y && Math.abs(d.z)<=a.avoidL1.z))
                    || (a.avoidCrit == SpatialDist.AvoidanceCriterion.L2 && (d.length()<=a.avoidL2))) {
                        okay = false;
                }
            }
          }
        }
        return okay;
    }
    @Override
    public void dump(String mess) {
        super.dump(mess);
        for (int i=0; i<neframes; i++) {
            EulerFrame f = eframes[i];
            System.out.printf(mess + "%d   %f %f %f  %f %f %f\n", i,
                f.origin().x, f.origin().y, f.origin().z, f.rot().x, f.rot().y, f.rot().z);
        }
    }
    @Override
    public void dump() {
        super.dump();
        dump("");
    }
}
