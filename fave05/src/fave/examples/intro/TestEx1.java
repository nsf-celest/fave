
/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */

package fave.examples.intro;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class TestEx1 extends FaveSimpleAppIntro {
    
    @Override
    public void localSceneCreate() {
        sg.createModelNode("tree");
        sg.loadModel("tree", "Models/tree2-prob-leaf/tree2-p050.j3o");
        sg.addChild("topNode", "tree");
        graphicsUtility.initLights("play");
    }
  
    public static void main(String[] args) {
        TestEx1 app = new TestEx1();
        app.start();
    }
}
