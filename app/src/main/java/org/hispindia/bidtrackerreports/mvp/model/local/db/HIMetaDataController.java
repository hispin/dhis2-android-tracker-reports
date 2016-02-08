package org.hispindia.bidtrackerreports.mvp.model.local.db;

import android.app.Application;

import java.util.List;

/**
 * Created by nhancao on 2/7/16.
 */
public class HIMetaDataController {

    HIDBHelper db;

    public HIMetaDataController(Application application) {
        db = new HIDBHelper(application.getApplicationContext());
    }

    public List<HIDBbidrow> getHIDBbidrowLocal() {
        return db.getAllBid();
    }

    public void cacheHIDBbidrowLocal(List<HIDBbidrow> localList) {
        db.deleteBid();
        for (HIDBbidrow item : localList) {
            db.insertBid(item);
        }
    }
}
