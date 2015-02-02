/* ParseCoreBase.java
 *
 * Developed as part of the Celeste FLAME project, 2013-2015
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 * 
 * License ...
 */

package fave.common.parsecore;


public class ParseCoreBase {
    String name = null;
    static int count = 0;
    static String defaultName(String base) {
        return base+(count++);
    }
}
