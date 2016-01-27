package org.hispindia.android.core.ui.activity;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.hispindia.android.core.utils.HICUtils;

import butterknife.ButterKnife;

public abstract class HICActivityBase extends AppCompatActivity {

    NCMCActivityReceiverBase myReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myReceiver = new NCMCActivityReceiverBase();
        IntentFilter filter = new IntentFilter();
        filter.addAction(HICUtils.ACTIVITY_RECEIVE);
        registerReceiver(myReceiver, filter);

        injectDependencies();
        final int layoutId = getContentViewId();
        if (layoutId == 0) {
            final View contentView = getContentView();
            if (contentView != null) {
                setContentView(contentView);
                injectViews();
            }
        } else {
            setContentView(layoutId);
            injectViews();
        }
        setupViews();
    }

    protected abstract void setupViews();

    /**
     * Create content view, this is fallback method to create content view if getContentViewId() method return 0.
     *
     * @return Content View.
     */
    protected View getContentView() {
        return null;
    }

    /**
     * Return layout's id to be set on setContentView(int layoutId) method.
     *
     * @return layout's id or 0 to use getContentView().
     */
    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected abstract void injectDependencies();

    /**
     * Use to inject views which declared by using Annotation @InjectView.
     */
    protected void injectViews() {
        ButterKnife.bind(this);
    }

    protected abstract void onReceive(Context context, Intent intent);

    class NCMCActivityReceiverBase extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            HICActivityBase.this.onReceive(context, intent);
        }
    }

}