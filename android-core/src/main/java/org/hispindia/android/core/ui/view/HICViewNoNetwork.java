package org.hispindia.android.core.ui.view;
/**
 * Created by NhanCao on 13-Sep-15.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.hispindia.android.core.R;


public class HICViewNoNetwork extends RelativeLayout {

    private ImageView ivIcon;

    public HICViewNoNetwork(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_no_network, this);
    }

    public HICViewNoNetwork(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HICViewNoNetwork(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivIcon = (ImageView) this.findViewById(R.id.ivIcon);
    }

    public ImageView getIconView() {
        return ivIcon;
    }
}
