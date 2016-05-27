package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIbidbirthrow;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIAdapterBirthNotificationReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;

    private boolean loadDone;
    private boolean filter;
    private List<HIbidbirthrow> hibidbirthRowList;
    private List<HIbidbirthrow> originList;

    public HIAdapterBirthNotificationReport() {
        hibidbirthRowList = new ArrayList<>();
        originList = new ArrayList<>();
        loadDone = false;
        filter = false;

    }

    public void setHibidbirthRowList(List<HIbidbirthrow> hibidRowList) {
        this.hibidbirthRowList = hibidRowList;
        notifyDataSetChanged();
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    public void revertList() {
        hibidbirthRowList.clear();
        for (HIbidbirthrow item : originList) {
            hibidbirthRowList.add(item);
        }
    }

    public void updateRow(HIDBbidrow hibidRow) {
        if (originList == null) {
            originList = new ArrayList<>();
        }
        boolean flag = false;
        for (int i = 0; i < originList.size(); i++) {
            HIbidbirthrow item = originList.get(i);

        }

        notifyDataSetChanged();
    }

    public final static String TAG = HIAdapterBirthNotificationReport.class.getSimpleName();

    public void filter(String etStartDate, String etEndDate) {
        filter = true;
        hibidbirthRowList.clear();
//
//        DateTime startD = DateTime.parse(etStartDate, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
//        DateTime endD = DateTime.parse(etEndDate, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

        DateTime startD = DateTime.parse(etStartDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
        DateTime endD = DateTime.parse(etEndDate, DateTimeFormat.forPattern("yyyy-MM-dd"));

        Log.e("LOG", "Start Date" + startD);
        Log.e("LOG", "End Date" + endD);


        for (HIbidbirthrow item : originList) {
            if (TextUtils.isEmpty(item.getDob()) || item.getDob().equals("0")) continue;
            try {
                DateTime item_dob = DateTime.parse(item.getDob(), DateTimeFormat.forPattern("yyyy-MM-dd"));

                Log.e("LOG", "item_dob" + item_dob);
                Log.e("LOG", "item_get_dob" + item.getDob());
                if (item_dob.isBefore(endD) && item_dob.isAfter(startD)) {
                    hibidbirthRowList.add(item);
                    Log.e("LOG ", " success hibidRowList " + hibidbirthRowList);

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "filter: " + e.toString());
            }
        }

        Log.e("LOG ", "filterDemandbydate: " + startD + " - " + endD + " Size root list: " + originList.size() + " Size list: " + hibidbirthRowList.size());

        if (hibidbirthRowList.size() == 0) {
            Log.e("LOG", "Fail");
            Log.e("LOG ", "filterDemandbydate: " + startD + " - " + endD + " Size root list: " + originList.size() + " Size list: " + hibidbirthRowList.size());

            //Toast.makeText(this , "Hello Android !!!", Toast.LENGTH_SHORT).show();

        } else {
            Log.e("LOG", "Success");
        }

        notifyDataSetChanged();
        etStartDate = "";
        etEndDate = "";

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
            return new HIAdapterFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_birth_notifications_row, parent, false);
            return new HIAdapterReportHolder(v, parent.getContext());
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HIAdapterFooterHolder) {
            HIAdapterFooterHolder viewHolder = (HIAdapterFooterHolder) holder;
            if (!loadDone) viewHolder.vProgressBar.setVisibility(View.VISIBLE);
            else viewHolder.vProgressBar.setVisibility(View.GONE);
        } else if (holder instanceof HIAdapterReportHolder) {
            HIbidbirthrow row = getItem(position);
            HIAdapterReportHolder viewHolder = (HIAdapterReportHolder) holder;


            setAttr(row.getFirstName(), viewHolder.tvFirstName);
            setAttr(row.getLastName(), viewHolder.tvChildName);
            setAttr(row.getDob(), viewHolder.tvdob);
            setAttr(row.getGender(), viewHolder.tvgender);
            setAttr(row.getVillage(), viewHolder.tvvillage);
            setAttr(row.getZone(), viewHolder.tvzone);

      
        }
    }

    public List<HIbidbirthrow> getList() {
        if (!filter) return originList;
        return hibidbirthRowList;
    }

    @Override
    public int getItemCount() {
        return getList().size() + 1;
    }

    private HIbidbirthrow getItem(int position) {
        return getList().get(position);
    }

    private boolean isPositionFooter(int position) {
        return position == getList().size();
    }

    public void setAttr(String item, TextView tv) {
        if (item != null) tv.setText(item);
        else tv.setText("");
    }

    public void setDe(String item, TextView tv, ImageView img, View view) {
        if (item != null && !item.trim().equals("")) {
            view.setVisibility(View.VISIBLE);
            if (item.trim().toLowerCase().equals("true")) {
                img.setBackgroundResource(R.drawable.ic_checkmark_holo_light);
                img.setVisibility(View.VISIBLE);
                tv.setVisibility(View.GONE);
            } else {
                img.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                tv.setText(item);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }

    class HIAdapterReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvOrder)
        TextView tvOrder;
        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvFirstName)
        TextView tvFirstName;
        @Bind(R.id.tvgender)
        TextView tvgender;
        @Bind(R.id.tvvillage)
        TextView tvvillage;
        @Bind(R.id.tvzone)
        TextView tvzone;

        @Bind(R.id.tvChildName)
        TextView tvChildName;

        @Bind(R.id.tvdob)
        TextView tvdob;


        Context context;

        public HIAdapterReportHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vProgressBar)
        ProgressBar vProgressBar;
        Context context;

        public HIAdapterFooterHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

    }
}
