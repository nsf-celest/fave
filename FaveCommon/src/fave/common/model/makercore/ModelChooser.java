/*
 * Developed as part of the Celeste FLAME project, 2013
 */
package fave.common.model.makercore;

import com.jme3.scene.Node;
import java.util.logging.Logger;

/**
 * Used for choosing from a set of predetermined models
 * In particular, getting clones
 * Uses ModelPlacer to issue clones
 * @author Erik Brisson <ebrisson@bu.edu>
 */
public class ModelChooser extends VariModelMaker {
    static final Logger logger = Logger.getLogger(ModelChooser.class.getName());
    int nmodels;
    ModelPlacer[] mplacer;
    String[] musename;
    String[] mgoodness;
    int[] nclone;
    int[] ihandle;
    boolean doscale = false;
    float msize;
    public ModelChooser (int nmodels) {
        this.nmodels = nmodels;
        mplacer = new ModelPlacer[nmodels];
        musename = new String[nmodels];
        mgoodness = new String[nmodels];
        nclone = new int[nmodels];
        ihandle = new int[nmodels];
        for (int i=0; i<nmodels; i++) {
             mplacer[i] = null;
             nclone[i] = 0;
        }
    }
    public void setModel(int index, ModelPlacer modelPlacer) {
        mplacer[index] = modelPlacer;
        if (doscale)
            mplacer[index].setScaleDiagTo(msize);
    }
    /*
    public ModelPlacer setModel(int index, Spatial model, String modelUseName) {
        mplacer[index] = new ModelPlacer(model);
        if (doscale) {
            mplacer[index].setScaleDiagTo(msize);
        }
        musename[index] = modelUseName;
        return mplacer[index];
    }
    public void setModel(int index, ModelPlacer modelPlacer, String modelUseName) {
        mplacer[index] = modelPlacer;
        if (doscale) {
            mplacer[index].setScaleDiagTo(msize);
        }
        musename[index] = modelUseName;
    }
    public void setModel(int index, ModelPlacer modelPlacer, String modelUseName, String goodness) {
        mplacer[index] = modelPlacer;
        if (doscale) {
            mplacer[index].setScaleDiagTo(msize);
        }
        musename[index] = modelUseName;
        mgoodness[index] = goodness;
    }
    public void setModel(int index, ModelPlacer modelPlacer, int ihandle) {
        mplacer[index] = modelPlacer;
        if (doscale) {
            mplacer[index].setScaleDiagTo(msize);
        }
        this.ihandle[index] = ihandle;
    }
    */
    /*
    public void setModelFaveAsset(int index, FaveAssets faveAssets,
                                  String modelType, String modelUseName, String goodness) {
        mplacer[index] = new ModelPlacer(faveAssets.fromType(modelType, modelUseName));
        if (doscale) {
            mplacer[index].setScaleDiagTo(msize);
        }
        musename[index] = modelUseName;
        mgoodness[index] = goodness;
    }
    */
    public Node getClone(int ivar) {
        nclone[ivar]++;
        return mplacer[ivar].getClonePlaced();
    }
    
    public Node getModel(int ivar) {
        return getClone(ivar);
    }
    public int ntypes() {
        return nmodels;
    }
    
    public int getNclone(int ivar) {
        return nclone[ivar];
    }
    public void setSize(float size) {
        for (int i=0; i<nmodels; i++) {
            if (mplacer[i] != null)
                 mplacer[i].setScaleDiagTo(size);
        }
        doscale = true;
        msize = size;
    }
    public String getModelUseName(int index) {
        return musename[index];
    }
    public String getGoodness(int index) {
        return mgoodness[index];
    }
    public int getIhandle(int index) {
        return ihandle[index];
    }
}
