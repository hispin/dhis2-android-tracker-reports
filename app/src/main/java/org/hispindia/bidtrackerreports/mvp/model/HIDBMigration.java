package org.hispindia.bidtrackerreports.mvp.model;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by NhanCao on 13-Sep-15.
 * details: https://github.com/realm/realm-java/blob/master/examples/migrationExample/src/main/java/io/realm/examples/realmmigrationexample/model/Migration.java
 */


public class HIDBMigration implements RealmMigration {
    public static final int DBVERSION = 0;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //https://realm.io/docs/java/latest/#migrations
    }
}