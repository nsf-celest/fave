/* TestEx3.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.examples.intro;

import fave.common.parsecore.ParseCoreRoot;
import fave.common.util.io.ReadTokens;
import favevex.SSExpr;


public class TestEx3 extends FaveSimpleAppIntro {
    
    @Override
    public void localSceneCreate() {
        ReadTokens getter = new ReadTokens();
        SSExpr expr = SSExpr.fromFile("data/simpexnew2.dat");
        expr.stringDump();
        ParseCoreRoot sroot = new ParseCoreRoot(expr);
        sroot.sgCall(sg, null);
        graphicsUtility.initLights("play");
    }
  
    public static void main(String[] args) {
        TestEx3 app = new TestEx3();
        app.start();
    }
}
