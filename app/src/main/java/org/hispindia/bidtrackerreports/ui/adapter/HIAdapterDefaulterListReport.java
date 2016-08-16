package org.hispindia.bidtrackerreports.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sourabh on 1/25/16.
 */
public class HIAdapterDefaulterListReport extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;

    @Bind(R.id.textView4)
    TextView textView4;

    private boolean loadDone;
    private boolean filter;
    private List<HIDBbidrow> hibidRowList;
    private List<HIDBbidrow> originList;

    //private Context context = this;
    public HIAdapterDefaulterListReport() {
        hibidRowList = new ArrayList<>();
        originList = new ArrayList<>();
        loadDone = false;
        filter = false;

    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    public void revertList(){
        hibidRowList.clear();
        for (HIDBbidrow item : originList) {
            hibidRowList.add(item);
        }
    }


    public void updateRow(HIDBbidrow hibidRow) {
        if (originList == null) {
            originList = new ArrayList<>();
        }
        Log.e(TAG,"originList"+ originList);
        Log.e(TAG,"originList hibidRowList "+ hibidRowList);

        boolean flag = false;
        for (int i = 0; i < originList.size(); i++) {
            HIDBbidrow item = originList.get(i);
            Log.e(TAG,"item:"+i+item);
            Log.e(TAG,"Sizl"+ originList.size());
            if (item.getOrder() == hibidRow.getOrder()) {
                Log.e(TAG,"hibidrow: "+ hibidRow.getOrder());
                originList.set(i, hibidRow);
                flag = true;

                break;
            }
        }
        if (!flag) {
            originList.add(hibidRow);
        }
        notifyDataSetChanged();
    }

    public final static String TAG = HIAdapterDefaulterListReport.class.getSimpleName();
    public void filter(String etStartDate, String etEndDate) {


        filter = true;
        hibidRowList.clear();

        DateTime startD = DateTime.parse(etStartDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
        DateTime endD = DateTime.parse(etEndDate, DateTimeFormat.forPattern("yyyy-MM-dd"));

        for (HIDBbidrow item : originList) {

            if (TextUtils.isEmpty(item.getDueDate()) || item.getDueDate().equals("0")) continue;
            try {

                DateTime item_dob = DateTime.parse(item.getDueDate(), DateTimeFormat.forPattern("yyyy-MM-dd"));
                Log.e(TAG,"Item Dob"+ item_dob);
                Log.e("LOG", "item_dob" + item_dob);
                Log.e("LOG", "item_get_dob" + item.getDob());
                Log.e("LOG", "item_birth_weight" + item.getbirthweight());
                if (item_dob.isBefore(endD) && item_dob.isAfter(startD)) {
                    textView4.setVisibility(View.VISIBLE);
                    hibidRowList.add(item);
                    Log.e("LOG ", " success DOB " + item.getDob());
                    Log.e("LOG ", " success birthweight " + item.getbirthweight());
                    Log.e("LOG ", " success Due " + item.getDueDate());

                }
                else
                {
                     textView4.setVisibility(View.VISIBLE);
                     Log.e(TAG,"No Results matched");

                   //  Toast.makeText(HIAdapterTodayScheduleReport.this.getApplicationContext(), "Please Choose Date First", Toast.LENGTH_SHORT);
                     //openDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "filter: " + e.toString());
            }
        }
        Log.e("LOG ", "filterDemandbydate: " + startD + " - " + endD + " Size root list: " + originList.size()+" Size list: " + hibidRowList.size());
        notifyDataSetChanged();
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(null); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("Alert Test");
        dialog.show();
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
                viewHolder.tvOrder.setTextColor(Color.GREEN);
                viewHolder.tvDueDate.setTextColor(Color.GREEN);
            }
            setAttr(row.getFirstName(), viewHolder.tvFirstName);
            setAttr(row.getChilName(), viewHolder.tvChildName);
            setAttr(row.getDob(), viewHolder.tvdob);
            setAttr(row.getbirthweight(), viewHolder.tvbirthweight);

            Log.e("DOB", "DOB" + row.getDob());

            //data element
            setDe(row.getBcg(), viewHolder.tvBCG, viewHolder.imgBCG, viewHolder.vBCG);
            setDe(row.getBcgScar(), viewHolder.tvBCGScar, viewHolder.imgBCGScar, viewHolder.vBCGScar);
            setDe(row.getBcgRepeatDose(), viewHolder.tvBCGRepeatDose, viewHolder.imgBCGRepeatDose, viewHolder.vBCGRepeatDose);
            setDe(row.getOpv0(), viewHolder.tvOPV0, viewHolder.imgOPV0, viewHolder.vOPV0);
            setDe(row.getOpv1(), viewHolder.tvOPV1, viewHolder.imgOPV1, viewHolder.vOPV1);
            setDe(row.getDptHepBHib1(), viewHolder.tvDptHepBHib1, viewHolder.imgDptHepBHib1, viewHolder.vDptHepBHib1);
            setDe(row.getPcv1(), viewHolder.tvPCV1, viewHolder.imgPCV1, viewHolder.vPCV1);
            setDe(row.getRv1(), viewHolder.tvRV1, viewHolder.imgRV1, viewHolder.vRV1);
            setDe(row.getOpv2(), viewHolder.tvOPV2, viewHolder.imgOPV2, viewHolder.vOPV2);
            setDe(row.getPcv2(), viewHolder.tvPCV2, viewHolder.imgPCV2, viewHolder.vPCV2);
            setDe(row.getRv2(), viewHolder.tvRV2, viewHolder.imgRV2, viewHolder.vRV2);
            setDe(row.getOpv3(), viewHolder.tvOPV3, viewHolder.imgOPV3, viewHolder.vOPV3);
            setDe(row.getDptHepBHib3(), viewHolder.tvDptHepBHib3, viewHolder.imgDptHepBHib3, viewHolder.vDptHepBHib3);
            setDe(row.getPcv3(), viewHolder.tvPCV3, viewHolder.imgPCV3, viewHolder.vPCV3);
            setDe(row.getOpv4(), viewHolder.tvOPV4, viewHolder.imgOPV4, viewHolder.vOPV4);
            setDe(row.getMeasles1(), viewHolder.tvMeasles1, viewHolder.imgMeasles1, viewHolder.vMeasles1);
            setDe(row.getMeasles2(), viewHolder.tvMeasles2, viewHolder.imgMeasles2, viewHolder.vMeasles2);
        }
    }

    public List<HIDBbidrow> getList(){
        if(!filter) return originList;
        return hibidRowList;
    }

    @Override
    public int getItemCount() {
        return getList().size() + 1;
    }

    private HIDBbidrow getItem(int position) {
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
        if (item != null && item.trim().equals("")) {
            view.setVisibility(View.VISIBLE);
            img.setBackgroundResource(R.drawable.ic_menu_my_calendar);
            img.setVisibility(View.VISIBLE);
            //tv.setVisibility(View.GONE);
        } else {
           // view.setVisibility(View.GONE);
        }
    }

    class HIAdapterToDayScheduleReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvOrder)
        TextView tvOrder;
//        @Bind(R.id.searchView)
//        SearchView searchView;
        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvFirstName)
        TextView tvFirstName;
        @Bind(R.id.tvChildName)
        TextView tvChildName;
        @Bind(R.id.tvdob)
        TextView tvdob;
        @Bind(R.id.tvbirthweight)
        TextView tvbirthweight;
        @Bind(R.id.vAttribute)
        LinearLayout vAttribute;
        @Bind(R.id.vDataElement)
        LinearLayout vDataElement;
        @Bind(R.id.vBCG)
        View vBCG;
        @Bind(R.id.vBCGScar)
        View vBCGScar;
        @Bind(R.id.vBCGRepeatDose)
        View vBCGRepeatDose;
        @Bind(R.id.vOPV0)
        View vOPV0;
        @Bind(R.id.vOPV1)
        View vOPV1;
        @Bind(R.id.vDptHepBHib1)
        View vDptHepBHib1;
        @Bind(R.id.vPCV1)
        View vPCV1;
        @Bind(R.id.vRV1)
        View vRV1;
        @Bind(R.id.vOPV2)
        View vOPV2;
        @Bind(R.id.vPCV2)
        View vPCV2;
        @Bind(R.id.vRV2)
        View vRV2;
        @Bind(R.id.vOPV3)
        View vOPV3;
        @Bind(R.id.vDptHepBHib3)
        View vDptHepBHib3;
        @Bind(R.id.vPCV3)
        View vPCV3;
        @Bind(R.id.vOPV4)
        View vOPV4;
        @Bind(R.id.vMeasles1)
        View vMeasles1;
        @Bind(R.id.vMeasles2)
        View vMeasles2;
        @Bind(R.id.tvBCG)
        TextView tvBCG;
        @Bind(R.id.tvBCGScar)
        TextView tvBCGScar;
        @Bind(R.id.tvBCGRepeatDose)
        TextView tvBCGRepeatDose;
        @Bind(R.id.tvOPV0)
        TextView tvOPV0;
        @Bind(R.id.tvOPV1)
        TextView tvOPV1;
        @Bind(R.id.tvDptHepBHib1)
        TextView tvDptHepBHib1;
        @Bind(R.id.tvPCV1)
        TextView tvPCV1;
        @Bind(R.id.tvRV1)
        TextView tvRV1;
        @Bind(R.id.tvOPV2)
        TextView tvOPV2;
        @Bind(R.id.tvPCV2)
        TextView tvPCV2;
        @Bind(R.id.tvRV2)
        TextView tvRV2;
        @Bind(R.id.tvOPV3)
        TextView tvOPV3;
        @Bind(R.id.tvDptHepBHib3)
        TextView tvDptHepBHib3;
        @Bind(R.id.tvPCV3)
        TextView tvPCV3;
        @Bind(R.id.tvOPV4)
        TextView tvOPV4;
        @Bind(R.id.tvMeasles1)
        TextView tvMeasles1;
        @Bind(R.id.tvMeasles2)
        TextView tvMeasles2;
        @Bind(R.id.imgBCG)
        ImageView imgBCG;
        @Bind(R.id.imgBCGScar)
        ImageView imgBCGScar;
        @Bind(R.id.imgBCGRepeatDose)
        ImageView imgBCGRepeatDose;
        @Bind(R.id.imgOPV0)
        ImageView imgOPV0;
        @Bind(R.id.imgOPV1)
        ImageView imgOPV1;
        @Bind(R.id.imgDptHepBHib1)
        ImageView imgDptHepBHib1;
        @Bind(R.id.imgPCV1)
        ImageView imgPCV1;
        @Bind(R.id.imgRV1)
        ImageView imgRV1;
        @Bind(R.id.imgOPV2)
        ImageView imgOPV2;
        @Bind(R.id.imgPCV2)
        ImageView imgPCV2;
        @Bind(R.id.imgRV2)
        ImageView imgRV2;
        @Bind(R.id.imgOPV3)
        ImageView imgOPV3;
        @Bind(R.id.imgDptHepBHib3)
        ImageView imgDptHepBHib3;
        @Bind(R.id.imgPCV3)
        ImageView imgPCV3;
        @Bind(R.id.imgOPV4)
        ImageView imgOPV4;
        @Bind(R.id.imgMeasles1)
        ImageView imgMeasles1;
        @Bind(R.id.imgMeasles2)
        ImageView imgMeasles2;

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