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
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIAdapterTodayScheduleReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;

    private boolean loadDone;
    private List<HIDBbidrow> hibidRowList;

    public HIAdapterTodayScheduleReport() {
        hibidRowList = new ArrayList<>();
        loadDone = false;
    }

    public void setHibidRowList(List<HIDBbidrow> hibidRowList) {
        this.hibidRowList = hibidRowList;
        notifyDataSetChanged();
    }

    public boolean isLoadDone() {
        return loadDone;
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    public void updateRow(HIDBbidrow hibidRow) {
        if (hibidRowList == null) {
            hibidRowList = new ArrayList<>();
        }
        boolean flag = false;
        for (int i = 0; i < hibidRowList.size(); i++) {
            HIDBbidrow item = hibidRowList.get(i);
            if (item.getOrder() == hibidRow.getOrder()) {
                hibidRowList.set(i, hibidRow);
                flag = true;
                break;
            }
        }
        if (!flag) {
            hibidRowList.add(hibidRow);
        }
        notifyDataSetChanged();
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
            return new HIAdapterToDayScheduleFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_today_schedule_row, parent, false);
            return new HIAdapterToDayScheduleReportHolder(v, parent.getContext());
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HIAdapterToDayScheduleFooterHolder) {
            HIAdapterToDayScheduleFooterHolder viewHolder = (HIAdapterToDayScheduleFooterHolder) holder;
            if (!loadDone) viewHolder.vProgressBar.setVisibility(View.VISIBLE);
            else viewHolder.vProgressBar.setVisibility(View.GONE);
        } else if (holder instanceof HIAdapterToDayScheduleReportHolder) {
            HIDBbidrow row = getItem(position);
            HIAdapterToDayScheduleReportHolder viewHolder = (HIAdapterToDayScheduleReportHolder) holder;
            viewHolder.tvOrder.setText("#" + row.getOrder());
            viewHolder.tvDueDate.setText(row.getDueDate());
            if (row.getIsOverdue()) {
                viewHolder.tvOrder.setTextColor(Color.RED);
                viewHolder.tvDueDate.setTextColor(Color.RED);
            } else {
                viewHolder.tvOrder.setTextColor(Color.WHITE);
                viewHolder.tvDueDate.setTextColor(Color.WHITE);
            }

            viewHolder.vAttribute.removeAllViews();
            setDataElement("First Name", row.getFirstName(), viewHolder.context, viewHolder.vAttribute);
            setDataElement("Child Number", row.getChilName(), viewHolder.context, viewHolder.vAttribute);

            //data element
            viewHolder.vDataElement.removeAllViews();
            setDataElement("BCG", row.getBcg(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("BCG Scar", row.getBcgScar(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("BCG Repeat Dose", row.getBcgRepeatDose(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("OPV 0", row.getOpv0(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("OPV 1", row.getOpv1(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("DPT-HepB-Hib1", row.getDptHepBHib1(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("PCV 1", row.getPcv1(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("RV 1", row.getRv1(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("OPV 2", row.getOpv2(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("PCV 2", row.getPcv2(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("RV 2", row.getRv2(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("OPV 3", row.getOpv3(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("DPT-HepB-Hib3", row.getDptHepBHib3(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("PCV 3", row.getOpv3(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("OPV 4", row.getOpv4(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("Measles 1", row.getMeasles1(), viewHolder.context, viewHolder.vDataElement);
            setDataElement("Measles 2", row.getMeasles2(), viewHolder.context, viewHolder.vDataElement);
        }
    }

    @Override
    public int getItemCount() {
        return hibidRowList.size() + 1;
    }

    private HIDBbidrow getItem(int position) {
        return hibidRowList.get(position);
    }

    private boolean isPositionFooter(int position) {
        return position == hibidRowList.size();
    }

    public void setDataElement(String title, String item, Context context, LinearLayout view) {
        view.post(() -> {
            LinearLayout lv = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.hiviewitem_today_schedule_row_item, null, false);
            TextView tvLeft = (TextView) lv.findViewById(R.id.tvLeft);
            TextView tvRight = (TextView) lv.findViewById(R.id.tvRight);
            ImageView imageView = (ImageView) lv.findViewById(R.id.imgIcon);
            tvLeft.setText(title);
            if (item == null || !item.equals("-*#hidefield#*-")) {
                if (item == null) {
                    imageView.setBackgroundResource(R.drawable.ic_menu_my_calendar);
                    imageView.setVisibility(View.VISIBLE);
                    tvRight.setVisibility(View.GONE);
                } else if (item.trim().toLowerCase().equals("true") || item.trim().equals("")) {
                    imageView.setBackgroundResource(R.drawable.ic_checkmark_holo_light);
                    imageView.setVisibility(View.VISIBLE);
                    tvRight.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.GONE);
                    tvRight.setVisibility(View.VISIBLE);
                    tvRight.setText(item);
                }
                if (view != null) view.addView(lv);
            }
        });

    }

    class HIAdapterToDayScheduleReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvOrder)
        TextView tvOrder;
        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvFirstName)
        TextView tvFirstName;
        @Bind(R.id.tvChildName)
        TextView tvChildName;
        @Bind(R.id.vAttribute)
        LinearLayout vAttribute;
        @Bind(R.id.vDataElement)
        LinearLayout vDataElement;
        Context context;

        public HIAdapterToDayScheduleReportHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterToDayScheduleFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vProgressBar)
        ProgressBar vProgressBar;
        Context context;

        public HIAdapterToDayScheduleFooterHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

    }
}
