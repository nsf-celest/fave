/* GamePlaySettings.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package gameplay;


public class GamePlaySettings {
    String navmode = null;
    public void setNavMode(String mode) {
        navmode = mode;
    }
    public String getNavMode() {
        return navmode;
    }
}
