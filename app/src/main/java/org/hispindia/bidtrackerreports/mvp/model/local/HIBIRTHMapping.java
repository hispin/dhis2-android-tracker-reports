package org.hispindia.bidtrackerreports.mvp.model.local;

import org.hispindia.bidtrackerreports.mvp.model.local.db.HIbidbirthrow;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.HIParamBIDHardcode;

/**
 * Created by nhancao on 2/5/16.
 */
public class HIBIRTHMapping {

    public static HIbidbirthrow fromRemote(HIBIDBIRTHRow remote) {
        if (remote == null) return null;
        HIbidbirthrow local = new HIbidbirthrow();


        for (HIBIDBIRTHRowItem item : remote.getDataElementList()) {
            if (item.getId().equals(HIParamBIDHardcode.DE.get("tgOliIchxda").getId())) {
                local.firstName = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("PsfSI5MK181").getId())) {
                local.lastname = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("o4rl8WVfTKX").getId())) {
                local.dob = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("q9voDSX20k6").getId())) {
                local.gender = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("Bq9p5j2e7gL").getId())) {
                local.village = item.getValue();
            } else if (item.getId().equals(HIParamBIDHardcode.DE.get("YqBeXCABhhk").getId())) {
                local.zone = item.getValue();
            }
        }

        return local;
    }


}
