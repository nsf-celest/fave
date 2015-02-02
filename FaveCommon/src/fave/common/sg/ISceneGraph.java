/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 *
 * The reason for providing this layer on top of jme is twofold:
 * (1) Provide a protocol for communication between applications, e.g., a scene
 *     creator that is independent of any graphics package could stochastically
 *     create scenes, and send ascii command stream to simple app running on
 *     any platform
 * (2) Create a level of abstraction for agents, so the implementation could
 *     build a complicated scene graph internally, but just expose certain
 *     nodes by their ascii names, so that agents can manipulate them (move
 *     switch on/off, etc)
 * Some notes:
 * The ascii ids may come into being, meaning be exposed to an agent outside
 * the actual scenegraph, by these means:
 * (1) Agent calls loadModel (and provides the id)
 * (2) The sgImp creates one internally, and registers the id
 * (3) Agent calls a load routine (the loadModel calls a callback internally)
 *     and the load routin (in addition to the id specified by the loadModel
 *     call) creates and registers other ids (sub-ids).  I would expect these
 *     to follow a naming convention like {id}-{subid}
 * What are the choices that must be made?  Let's consider moving a wheel
 * on a truck, and let's suppose we are given a model with the body and four
 * separate wheels, with the wheels in their proper locations).  Options:
 * (1) Agent does all creation, i.e., calls loadModel 5 times: loadModel("truck-body"),
 *     loadModel("tire-front-left"), etc.  It can attach the tires to the body using
 *     addChild.
 *     (a) if each tire is in a local coord system st the tire is centered at the
 *         origin, then each tire will need to be under two transforms, a translation
 *         to put it in the proper location, and a local transformation that the
 *         agent can send rotation angles to.
 *     (b) if each tire has its vertis properly offset from the truck body, then
 *         only one transform node is needed (which comes with loadModel) but the
 *         rotation is harder for the agent - agent must compute rotation that will
 *         keep the tire in place.
 * (2) loadModel("truck") calls a callback that creates a main transform node "truck"
 *     and "truck-tire-front-left", etc.  "truck-tire-fron-left" is registered with
 *     an internal node, and xform commands sent to this node are processed as they
 *     would be with an agent-created node.   So the agent can send rotation angles to
 *     the tires which will rotate them properly.  This will work since the callback
 *     adds all the transform nodes in the chain needed.
 * (3) Similar to (2), except that the creator of the truck can use any structure it
 *     wants, and xorm calls are sent to yet more callbacks attached to a truck 
 *     object.  This requires that the sg cmd processor be able to know for each 
 *     modelName whether to make a callback or just do the thing using the basic
 *     sg operations.
 * Bottom line is that an application or agent can think of the ascii ids as
 * internal nodes.  There are some caveats with ids created by a model callback,
 * first that the name must be known or inferred, and second that it cannot be
 * detached.  These are "special" nodes that the callback has "exposed".  They
 * hooks that you can send xforms at.
*/
package fave.common.sg;

import com.jme3.math.Vector3f;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public interface ISceneGraph {
    public void aliasTopNode(String nodeName);
    public void createInternalNode(String nodeName);
    public void createModelNode(String nodeName);
    public void deleteNode(String nodeName);
    public void addChild(String nodeNameParent, String nodeNameChild);
    public void removeChild(String nodeNameParent, String nodeNameChild);
    public void loadModel(String nodeName, String modelName);
    public void xformPosRotScale(String nodeName, Vector3f vpos, Vector3f vrot, Vector3f vscale);
    public void xformPosLookPoint(String nodeName, Vector3f vpos, Vector3f vlookat, Vector3f vup);
    public void xformPosStareDir(String nodeName, Vector3f vpos, Vector3f vstaredir, Vector3f vup);
    public void starePickAttempt(String pickRootNodeName);
    public void frameTick(float tframe, int iframe);
}
