
/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */

package fave.examples.intro;

import fave.common.parsecore.ParseCoreRoot;
import fave.common.util.io.ReadTokens;
import favevex.SSExpr;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class TestEx2 extends FaveSimpleAppIntro {
    
    @Override
    public void localSceneCreate() {
        ReadTokens getter = new ReadTokens();
        /*
        ArrayList<String> sinput = getter.fromFile("data/simpex0.dat");
        if (sinput != null)
            for (String token : sinput)
                System.out.println(token);
        */
        SSExpr expr = SSExpr.fromFile("data/simpex0.dat");
        expr.stringDump();
        ParseCoreRoot sroot = new ParseCoreRoot(expr);
        sroot.sgCall(sg, null);
        graphicsUtility.initLights("orchard");
    }
  
    public static void main(String[] args) {
        fave.examples.intro.TestEx2 app = new fave.examples.intro.TestEx2();
        app.start();
    }
}
