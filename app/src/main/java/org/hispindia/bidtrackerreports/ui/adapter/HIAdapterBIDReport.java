package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRow;
import org.hispindia.bidtrackerreports.mvp.model.local.HIBIDRowItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIAdapterBIDReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;

    private boolean loadDone;
    private List<HIBIDRow> hibidRowList;

    public HIAdapterBIDReport() {
        hibidRowList = new ArrayList<>();
        loadDone = false;
    }

    public List<HIBIDRow> getHibidRowList() {
        return hibidRowList;
    }

    public void setHibidRowList(List<HIBIDRow> hibidRowList) {
        this.hibidRowList = hibidRowList;
    }

    public boolean isLoadDone() {
        return loadDone;
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    public void updateRow(HIBIDRow hibidRow) {
        if (hibidRowList == null) {
            hibidRowList = new ArrayList<>();
        }
        boolean flag = false;
        for (HIBIDRow item : hibidRowList) {
            if (item.getEventId().equals(hibidRow.getEventId())) {
                item.setDueDate(hibidRow.getDueDate());
                item.setIsOverDue(hibidRow.isOverDue());
                item.setTrackedEntityAttributeList(hibidRow.getTrackedEntityAttributeList());
                item.setDataElementList(hibidRow.getDataElementList());
                flag = true;
                notifyDataSetChanged();
                break;
            }
        }
        if (!flag) {
            hibidRowList.add(hibidRow);
            notifyDataSetChanged();
        }
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
            return new HIAdapterBIDFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_bid_row, parent, false);
            return new HIAdapterBIDReportHolder(v, parent.getContext());
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HIAdapterBIDFooterHolder) {
            HIAdapterBIDFooterHolder viewHolder = (HIAdapterBIDFooterHolder) holder;
            if (!loadDone) viewHolder.vProgressBar.setVisibility(View.VISIBLE);
            else viewHolder.vProgressBar.setVisibility(View.GONE);
        } else if (holder instanceof HIAdapterBIDReportHolder) {
            HIBIDRow currentItem = getItem(position);
            HIAdapterBIDReportHolder viewHolder = (HIAdapterBIDReportHolder) holder;
            viewHolder.tvOrder.setText("#" + currentItem.getOrder());
            viewHolder.tvDueDate.setText(currentItem.getDueDate());
            if (currentItem.isOverDue()) {
                viewHolder.tvOrder.setTextColor(Color.RED);
                viewHolder.tvDueDate.setTextColor(Color.RED);
                viewHolder.tvOverDue.setBackgroundResource(R.drawable.ic_checkmark_holo_light);
            } else {
                viewHolder.tvOrder.setTextColor(Color.WHITE);
                viewHolder.tvDueDate.setTextColor(Color.WHITE);
                viewHolder.tvOverDue.setBackgroundResource(0);
            }
            generaterRow(viewHolder.context, viewHolder.vAttribute, viewHolder.vDataElement, currentItem);
        }
    }

    @Override
    public int getItemCount() {
        return hibidRowList.size() + 1;
    }

    private HIBIDRow getItem(int position) {
        return hibidRowList.get(position);
    }

    private boolean isPositionFooter(int position) {
        return position == hibidRowList.size();
    }

    public void generaterRow(Context context, LinearLayout vAttribute, LinearLayout vDataElement, HIBIDRow row) {
        vAttribute.removeAllViews();
        vDataElement.removeAllViews();
        for (int i = 0; i < row.getTrackedEntityAttributeList().size(); i++) {
            HIBIDRowItem item = row.getTrackedEntityAttributeList().get(i);
            if (item.getValue() != null) {
                vAttribute.addView(createRowUI(context, item));
            }
        }

        for (int i = 0; i < row.getDataElementList().size(); i++) {
            HIBIDRowItem item = row.getDataElementList().get(i);
            if (item.getValue() == null || !item.getValue().equals("-*#hidefield#*-")) {
                vDataElement.addView(createRowUI(context, item));
            }
        }
    }

    private View createRowUI(Context context, HIBIDRowItem item) {
        LinearLayout lv = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.hiviewitem_bid_row_item, null, false);
        TextView tvLeft = (TextView) lv.findViewById(R.id.tvLeft);
        TextView tvRight = (TextView) lv.findViewById(R.id.tvRight);
        ImageView imageView = (ImageView) lv.findViewById(R.id.imgIcon);
        tvLeft.setText(item.getName());
        if (item.getValue() == null) {
            imageView.setBackgroundResource(R.drawable.ic_menu_my_calendar);
            imageView.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.GONE);
        } else if (item.getValue().trim().toLowerCase().equals("true") || item.getValue().trim().equals("")) {
            imageView.setBackgroundResource(R.drawable.ic_checkmark_holo_light);
            imageView.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.GONE);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(item.getValue());
        }
        return lv;
    }

    class HIAdapterBIDReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvOrder)
        TextView tvOrder;
        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvOverDue)
        TextView tvOverDue;
        @Bind(R.id.vAttribute)
        LinearLayout vAttribute;
        @Bind(R.id.vDataElement)
        LinearLayout vDataElement;
        Context context;

        public HIAdapterBIDReportHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterBIDFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vProgressBar)
        ProgressBar vProgressBar;
        Context context;

        public HIAdapterBIDFooterHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

    }
}
