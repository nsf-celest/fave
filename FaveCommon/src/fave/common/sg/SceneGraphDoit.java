/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.common.sg;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fave.common.jme.util.SGCompanionApply;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class SceneGraphDoit implements ISceneGraph {
    private static final Logger logger = Logger.getLogger(SceneGraphDoit.class.getName());
    AssetManager assetManager;
    boolean dumpAll = false;

    enum NODE_TYPE {INTERNAL, MODEL}; 
    class SNode {
        NODE_TYPE nodeType;
        Spatial s;
        CameraNode camNode = null;
        SNode(NODE_TYPE nodeType, Spatial s) {
            this.nodeType = nodeType;
            if (s == null)
                this.s = new Node();
            else
                this.s = s;
        }
    }
    Map<String, SNode> nameToNode = new HashMap<String, SNode>();
    Map<String, String> modelAliasMap = new HashMap<String, String>();
    SNode snodeTop;
    Node defaultCameraNode = new Node();
    SNode snodeDefaultCamera;
    final static public String topNodeNameDefault = "topNode";
    float maxPickDistance = 100f;
    
    public SceneGraphDoit(AssetManager assetManager, Node topNode) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.SceneGraphDoit");
        this.assetManager = assetManager;
        snodeTop = new SNode(NODE_TYPE.INTERNAL, topNode);
        nameToNode.put(topNodeNameDefault, snodeTop);  
        snodeDefaultCamera = new SNode(NODE_TYPE.INTERNAL, defaultCameraNode);
        nameToNode.put("defaultCamera", snodeDefaultCamera);
        topNode.attachChild(defaultCameraNode);
    }
    
    public void setMaxPickDistance(float dist) {
        maxPickDistance = dist;
    }
    
    public void addModelAlias(String modelAlias, String modelAsset) {
        modelAliasMap.put(modelAlias, modelAsset);
    }

    /*
    public void addModelCallback(String modelName, ModelCB cb) {
        modelCBMap.put(modelName, cb);
    }
    */

    public Node getNode(String nodeName) {
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL) {
            logger.log(Level.SEVERE, "cannot get node for {0})", new Object[]{nodeName});
            return null;
        }
        else
            return (Node)(sn.s);
    }
    
    public CameraNode getCamNode(String nodeName) {
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL) {
            logger.log(Level.SEVERE, "cannot get camera node for {0})", new Object[]{nodeName});
            return null;
        }
        else
            return sn.camNode;
    }
   
    public Spatial getSpatial(String nodeName) {
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.MODEL) {
            logger.log(Level.SEVERE, "cannot get node for {0})", new Object[]{nodeName});
            return null;
        }
        else
            return sn.s;
    }    
    
    // if you attach a cam to a node: cam will be kept
    // "in sync" with the node, i.e., will behave as if
    // physically attached to it
    public void attachCamera(String nodeName, Camera cam) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.attachCamera: nodeName = "+nodeName);
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL)
             logger.log(Level.SEVERE, "cannot apply attachCamera to sg node {0})", new Object[]{nodeName});
        else {
            sn.camNode = new CameraNode(nodeName, cam);
            ((Node)(sn.s)).attachChild(sn.camNode);
        }
    }
    
    public void detachCamera(String nodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.detachCamera: nodeName = "+nodeName);
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL)
             logger.log(Level.SEVERE, "cannot apply detachCamera to sg node {0})", new Object[]{nodeName});
        else
            sn.camNode = null;
    }
    
    // note that the top node can be referred to by multiple names.  Ever going to be a problem?
    public void aliasTopNode(String nodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.aliasTopNode: nodeName = "+nodeName);
        nameToNode.put(nodeName, snodeTop);
    }
       
    public void createInternalNode(String nodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.createInternalNode: nodeName = "+nodeName);
        SNode sn = new SNode(NODE_TYPE.INTERNAL, null);
        nameToNode.put(nodeName, sn);
    }
    
    public void createModelNode(String nodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.createModelNode: nodeName = "+nodeName);
        SNode sn = new SNode(NODE_TYPE.MODEL, null);
        nameToNode.put(nodeName, sn);
    }

    // This removes the node from the register.
    // Caller is responsible for removing all parent-child relationships.  (before?)
    public void deleteNode(String nodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.deleteNode: nodeName = "+"nodeName");
        if (nameToNode.containsKey(nodeName))
            nameToNode.remove(nodeName);
    }
    
    public void addChild(String nodeNameParent, String nodeNameChild) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.addChild: nodeNameParent, nodeNameChild = "+nodeNameParent+" "+nodeNameChild);
        SNode np = nameToNode.get(nodeNameParent);
        SNode nc = nameToNode.get(nodeNameChild);
        if (np==null || np.nodeType!=NODE_TYPE.INTERNAL || np.s==null || nc==null ||nc.s==null)
             logger.log(Level.SEVERE, "cannot apply addChild to sg nodes {0} {1})", new Object[]{nodeNameParent, nodeNameChild});
        else
            ((Node)(np.s)).attachChild(nc.s);
    }
    
    public void removeChild(String nodeNameParent, String nodeNameChild) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.removeChild: nodeNameParent, nodeNameChild = "+nodeNameParent+" "+nodeNameChild);
        SNode np = nameToNode.get(nodeNameParent);
        SNode nc = nameToNode.get(nodeNameChild);
        if (np==null || np.nodeType!=NODE_TYPE.INTERNAL || nc==null )
             logger.log(Level.SEVERE, "cannot apply removeChild to sg nodes {0} {1})", new Object[]{nodeNameParent, nodeNameChild});
        else
            ((Node)(np.s)).detachChild(nc.s);
    }
    
    public void xformPosRotScale(String nodeName, Vector3f vpos, Vector3f vrot, Vector3f vscale) {
        if (dumpAll) {
            System.out.println("SceneGraphDoit.xformPosRotScale: nodeName = "+nodeName); 
            System.out.println("   vpos = "+vpos);        
            System.out.println("   vrot = "+vrot);        
            System.out.println("   vscale = "+vscale);
        }
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL)
             logger.log(Level.SEVERE, "cannot apply transform to sg node {0})", new Object[]{nodeName});
        else {
            Node n = (Node)sn.s;
            n.setLocalTranslation(vpos);
            Quaternion q = new Quaternion();
            q.fromAngles(vrot.x, vrot.y, vrot.z);
            n.setLocalRotation(q);
            n.setLocalScale(vscale);
        }
    }
    
    public void xformPosLookPoint(String nodeName, Vector3f vpos, Vector3f vlookpt, Vector3f vup) {
        if (dumpAll) {
            System.out.println("SceneGraphDoit.xformPosLookAt: nodeName = :"+nodeName); 
            System.out.println("   vpos = "+vpos);        
            System.out.println("   vlookpt = "+vlookpt);        
            System.out.println("   vup = "+vup);
        }
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL)
             logger.log(Level.SEVERE, "cannot apply transform to sg node {0})", new Object[]{nodeName});
        else {
            Node n = (Node)sn.s;
            n.setLocalTranslation(vpos);       
            n.lookAt(vlookpt, vup); 
        }
    }

    public void xformPosStareDir(String nodeName, Vector3f vpos, Vector3f vstaredir, Vector3f vup) {
        if (dumpAll) {
            System.out.println("SceneGraphDoit.xformPosStareDir: nodeName = :"+nodeName); 
            System.out.println("   vpos = "+vpos);        
            System.out.println("   vstaredir = "+vstaredir);        
            System.out.println("   vup = "+vup);
        }
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.INTERNAL) {
             logger.log(Level.SEVERE, "cannot apply transform to sg node {0})", new Object[]{nodeName});
        }
        else {
            Node n = (Node)sn.s;
            n.setLocalTranslation(vpos);          
            Vector3f vlookat = new Vector3f(vpos);
            vlookat.addLocal(vstaredir);
            n.lookAt(vlookat, vup);
        }
    }

    public void loadModel(String nodeName, String modelName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.loadModel: nodeName, modelName = "+nodeName+" "+modelName);
        SNode sn = nameToNode.get(nodeName);
        if (sn==null || sn.nodeType!=NODE_TYPE.MODEL)
              logger.log(Level.SEVERE, "cannot load model {0} (for sg name {1})", new Object[]{modelName, nodeName});
        else {
            String assetPath = modelAliasMap.get(modelName);
            if (assetPath != null)
                sn.s = assetManager.loadModel(assetPath);
            else
                sn.s = assetManager.loadModel(modelName);
            sn.s.setName(nodeName);
        }
    }
    
    public void saveModel(String nodeName, String fullFileName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.saveModel: nodeName, fullFileName = "+nodeName+" "+fullFileName);
        Node n = getNode(nodeName);
        if (n==null)
            logger.log(Level.SEVERE, "cannot save model {0} (not found)", new Object[]{nodeName});
        else {     
            System.out.println("about to save "+fullFileName);
            File saveFile = new File(fullFileName);
            BinaryExporter exporter = new BinaryExporter();
            try {
                exporter.save(n, saveFile);
                System.out.println("Saved model");
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error: Failed to save model!");
            }
        }
    }
    
    Camera getDefaultCameraInternal() {
        Camera cam = null;
        CameraNode camNode = getCamNode("defaultCamera");
        if (camNode != null)
            cam =  camNode.getCamera();
        return cam;
    }
    
    public void starePickAttempt(String pickRootNodeName) {
        if (dumpAll)
            System.out.println("SceneGraphDoit.starePickAttempt: pickRootNodeName = "+pickRootNodeName);
        Node nodeCheck = getNode(pickRootNodeName);
        if (nodeCheck==null)
            logger.log(Level.SEVERE, "cannot apply starePickAttempt, model {0} (not found)", new Object[]{pickRootNodeName});
        else {
            Camera defCam = getDefaultCameraInternal();
            if (defCam==null)
                logger.log(Level.SEVERE, "cannot apply starePickAttempt, defaultCamera not found");
            else {
                boolean pickablePicked = false;
                CollisionResults results = new CollisionResults();
                Ray ray = new Ray(defCam.getLocation(), defCam.getDirection());
                nodeCheck.collideWith(ray, results);
                /* To look at all collisions   
                for (int i = 0; i < results.size(); i++) {
                    CollisionResult cr = results.getCollision(i);
                }      
                */
                if (results.size() > 0) {
                    CollisionResult closest = results.getClosestCollision();
                    float dist = closest.getDistance();
                    if (dist <= maxPickDistance) {
                        Vector3f pt = closest.getContactPoint();
                        Spatial geom = closest.getGeometry();
                        logger.log(Level.INFO, "picked geom name is {0}", new Object[]{geom.getName()});
                        while (geom != null) {
                            logger.log(Level.INFO, "try geom name is {0}.", new Object[]{geom.getName()});
                            SGCompanionApply sgpick = SGCompanionApply.fromSpatial(geom);
                            if (sgpick != null) {
                                sgpick.applyPick(pt, dist);
                                pickablePicked = true;
                                break;
                            }
                            geom = geom.getParent();
                        }
                        // String hit = closest.getGeometry().getName();
                        // System.out.println("  You picked " + hit + " at " + pt + ", " + dist + " feet away.");
                        /*
                        if (mark != null) {
                            mark.setLocalTranslation(closest.getContactPoint());
                            worldNode.attachChild(mark);
                        }
                        */
                    }
                } /*
                else {
                    if (mark != null)
                        worldNode.detachChild(mark);  
                }
                */
            }
        } 
    }
    
    public void frameTick(float tframe, int iframe) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

}
