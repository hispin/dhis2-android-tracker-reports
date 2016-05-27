package org.hispindia.bidtrackerreports.mvp.model.local.db;

import android.app.Application;

import java.util.List;

/**
 * Created by nhancao on 2/7/16.
 */
public class HIMetaDataBirthController {

    HIBIRTHHelper db;

    public HIMetaDataBirthController(Application application) {
        db = new HIBIRTHHelper(application.getApplicationContext());
    }

    public List<HIbidbirthrow> getHIDBbidrowLocal() {
        return db.getAllBid();
    }

    public void cacheHIDBbidrowLocal(List<HIbidbirthrow> localList) {
        db.deleteBid();
        for (HIbidbirthrow item : localList) {
            db.insertBid(item);
        }
    }
}
