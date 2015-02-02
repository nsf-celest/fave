/*
 * Developed as part of the CELEST FLAME project, 2013-2014
 */
package fave.common.app;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class FaveSimpleApplication extends AppGlob {
    @Override
    public void simpleInitApp() {
    }
    
    @Override
    public void simpleUpdate(float tpf) {         
    }

    @Override public void update() {
        super.update();  /* super.update calls simpleUpdate */
    }
    
    @Override public void stop() {
        super.stop();   
    }
}
