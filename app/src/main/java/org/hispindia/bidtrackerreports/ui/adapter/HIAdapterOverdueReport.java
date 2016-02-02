package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hisp.dhis.android.sdk.controllers.metadata.MetaDataController;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.HIAttribute;
import org.hispindia.bidtrackerreports.mvp.model.local.HIOverdueRow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sourabh on 2/2/2016.
 */
public class HIAdapterOverdueReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;

    private boolean loadDone;
    private List<HIOverdueRow> hiList;

    public HIAdapterOverdueReport() {
        hiList = new ArrayList<>();
        loadDone = false;
    }

    public List<HIOverdueRow> getHiList() {
        return hiList;
    }

    public void setHiList(List<HIOverdueRow> hiList) {
        this.hiList = hiList;
        notifyDataSetChanged();
    }

    public boolean isLoadDone() {
        return loadDone;
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_PROGRESS;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_progress, parent, false);
            return new HIAdapterOverdueFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_overdue_row, parent, false);
            return new HIAdapterOverdueReportHolder(v, parent.getContext());
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HIAdapterOverdueFooterHolder) {
            HIAdapterOverdueFooterHolder viewHolder = (HIAdapterOverdueFooterHolder) holder;
            if (!loadDone) viewHolder.vProgressBar.setVisibility(View.VISIBLE);
            else viewHolder.vProgressBar.setVisibility(View.GONE);
        } else if (holder instanceof HIAdapterOverdueReportHolder) {
            HIOverdueRow currentItem = getItem(position);
            HIAdapterOverdueReportHolder viewHolder = (HIAdapterOverdueReportHolder) holder;
            viewHolder.tvDueDate.setText(currentItem.dueDate);
            viewHolder.tvEvent.setText(MetaDataController.getProgram(currentItem.program).getName());
            generaterRow(viewHolder.context, viewHolder.vAttribute, currentItem);
        }
    }

    @Override
    public int getItemCount() {
        return hiList.size() + 1;
    }

    private HIOverdueRow getItem(int position) {
        return hiList.get(position);
    }

    private boolean isPositionFooter(int position) {
        return position == hiList.size();
    }

    public void generaterRow(Context context, LinearLayout vAttribute, HIOverdueRow row) {
        vAttribute.removeAllViews();

        if (row.attributes != null) {
            for (HIAttribute att : row.attributes) {
                vAttribute.addView(createRowUI(context, att));
            }
        }
    }

    private View createRowUI(Context context, HIAttribute item) {
        LinearLayout lv = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.hiviewitem_overdue_row_item, null, false);
        TextView tvLeft = (TextView) lv.findViewById(R.id.tvLeft);
        TextView tvRight = (TextView) lv.findViewById(R.id.tvRight);
        tvLeft.setText(item.displayName);
        tvRight.setText(item.value);
        return lv;
    }

    class HIAdapterOverdueReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvEvent)
        TextView tvEvent;
        @Bind(R.id.vAttribute)
        LinearLayout vAttribute;
        Context context;

        public HIAdapterOverdueReportHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterOverdueFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vProgressBar)
        ProgressBar vProgressBar;
        Context context;

        public HIAdapterOverdueFooterHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

    }
}
