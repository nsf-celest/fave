/*
 * Developed as part of the Celeste FLAME project, 2013-2014
 */
package fave.common.jme.util;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;
import fave.common.util.math.View;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class CollisionManagement {
    private static final Logger logger = Logger.getLogger(CollisionManagement.class.getName());
    AppStateManager stateManager;
    CharacterControl player;
    BulletAppState bulletAppState;
    Spatial collidables;
    View resetView;
    
    public CollisionManagement(AppStateManager stateManager, Spatial collidables, View resetView) {
        this.collidables = collidables;
        this.stateManager = stateManager;
        this.resetView = new View(resetView);
        init();
    }
    final void init() {
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(collidables);
        RigidBodyControl landscape = new RigidBodyControl(sceneShape, 0);
        collidables.addControl(landscape);
        
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 100f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(0);
        player.setFallSpeed(0);
        player.setGravity(0);     
        player.setPhysicsLocation(resetView.eyePosn);
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(player);
    }
    public CharacterControl getPlayer() {
        return player;
    }
}
