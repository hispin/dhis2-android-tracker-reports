package org.hispindia.android.core.dagger;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.app.Activity;

import org.hispindia.android.core.dagger.module.HICModuleActivity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 */
@HICIPerActivity // Subtypes of ActivityComponent should be decorated with @PerActivity.
@Component(modules = HICModuleActivity.class)
public interface HICIActivityComponent {
    Activity activity(); // Expose the activity to sub-graphs.
}