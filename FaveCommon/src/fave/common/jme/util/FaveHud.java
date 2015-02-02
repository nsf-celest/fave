/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.jme.util;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class FaveHud {
    /*
    protected GameState.State curState = State.START;
    protected float tInState, tCountDown;
    */
    protected Node guiNode;
    protected AppSettings settings;
    protected BitmapFont guiFontDefault;
    protected BitmapFont guiFontCalibri;
    protected BitmapFont guiFontMVBoli;
    // protected BitmapText scoreText[] = null;^M
    /*^M
    int ntext = 0;
    static int score[] = null;^M
    static float xpic[] = null;^M
    static float ypic[] = null;^M
    static boolean basketFull = false;^M
    */

    public class FontManager {
        public BitmapFont guiFont;
        String name;
        public FontManager(String fontName) {
            name = fontName;
            guiFont = assetManager.loadFont("Interface/Fonts/"+fontName+".fnt");
        }
        public BitmapText newText(float size) {  // size in screen fractions
            BitmapText text = new BitmapText(guiFont, false);
            // text.setSize(guiFont.getCharSet().getRenderedSize()/4 * 3); 
            text.setSize(2*size*settings.getHeight());
            return text;
        }
    }
    
    protected FontManager fontDefault;
    protected FontManager fontCalibri;
    protected FontManager fontMVBoli;
    
    protected AssetManager assetManager;
    public FaveHud(AssetManager assetManager, AppSettings settings, Node guiNode) {
        this.guiNode = guiNode;
        this.settings = settings;
        this.assetManager = assetManager;
        guiNode.detachAllChildren();
        // guiFontDefault = assetManager.loadFont("Interface/Fonts/Default.fnt");
        // guiFontCalibri = assetManager.loadFont("Interface/Fonts/Calibri.fnt");
        // guiFontMVBoli = assetManager.loadFont("Interface/Fonts/MVBoli.fnt");
        fontDefault = new FontManager("Default");
        fontCalibri = new FontManager("Calibri");
        fontMVBoli  = new FontManager("MVBoli");
        guiFontDefault = fontDefault.guiFont;
        guiFontCalibri = fontCalibri.guiFont;
        guiFontMVBoli  = fontMVBoli.guiFont;
    }
    public void update() {
        // picture.setImage(assetManager, "Interface/statechange.png", true);
    }
    /*
    public void setState(State state, float tInState, float tCountDown) {
        curState = state;
        this.tInState = tInState;
        this.tCountDown = tCountDown;
    }
    */
    public void useCrossHairs() {
       // BitmapText ch = new BitmapText(guiFontDefault, false);
       // ch.setSize(guiFontDefault.getCharSet().getRenderedSize() * 2);
       BitmapText ch = fontDefault.newText(0.025f);
       ch.setText("+");
       ch.setLocalTranslation(
           settings.getWidth() / 2 - guiFontDefault.getCharSet().getRenderedSize() / 3 * 2,
           settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
       guiNode.attachChild(ch);
    }    
}
