/* TestEx4.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.examples.intro;

import fave.common.parsecore.ParseCoreRoot;
import favevex.SSExpr;

public class ExampleBasic extends FaveSimpleAppIntro {
    static String[] configFiles = null;
    ParseCoreRoot sroot = null;
    
    @Override
    public void simpleInitApp() {
        if (playsettings == null)
            System.out.println("ExampleBasic null playsettings");
        for (String config : configFiles) {
            SSExpr expr = SSExpr.fromFile(config);
            // expr.stringDump();
            if (sroot == null)
                sroot = new ParseCoreRoot(expr);
            else
                sroot.parse(expr);
        }
        super.simpleInitApp();
    }
        
    @Override
    public void localSceneCreate() {
        sroot.sgCall(sg, navsys);
        graphicsUtility.initLights("play");
    }
  
    public static void main(String[] args) {
        configFiles = args;
        ExampleBasic app = new ExampleBasic();
        app.start();
    }
}
    
