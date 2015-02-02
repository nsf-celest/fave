/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */

package favevex;

import fave.common.util.io.ReadTokens;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import java.util.ArrayList;

/**
 *
 * @author ebrisson
 */
public class SSTestMain extends SimpleApplication {

    public static void main(String[] args) {
        SSTestMain app = new SSTestMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        ReadTokens getter = new ReadTokens();
        ArrayList<String> sinput = getter.fromFile("data/vex0.dat");
        if (sinput != null)
            for (String token : sinput)
                System.out.println(token);
        SSExpr expr = SSExpr.fromFile("data/vex0.dat");
        expr.stringDump();
        VxRoot vroot = new VxRoot(expr);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
