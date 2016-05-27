package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow3;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIAdapterStockDemandReport3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int abc = 0;
    public final static String TAG = HIAdapterStockDemandReport3.class.getSimpleName();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;
    private static final int TYPE_HEADER = 2;
    public List<String> hiStockRowList3;
    public HashMap<String, Integer> inhand;
    public HashMap<String, Integer> demand;
    public HashMap<String, Integer> difference;

    private boolean loadDone;

    public HIAdapterStockDemandReport3() {
        hiStockRowList3 = new ArrayList<>();
        inhand = new HashMap<>();
        demand = new HashMap<>();
        difference = new HashMap<>();

        hiStockRowList3.add("RV");
        hiStockRowList3.add("PCV");
        hiStockRowList3.add("Measles");
        hiStockRowList3.add("DPT");
        hiStockRowList3.add("BCG");
        hiStockRowList3.add("OPV");
        // hiStockRowList.add("Total");
        loadDone = false;
    }


    public void setInHandList3(List<HIStockRow3> inHandList) {
        for (HIStockRow3 item : inHandList) {
            inhand.put(item.getName().substring(0, item.getName().indexOf(" ")), Integer.parseInt(item.getValue()));
            Log.e(TAG, "inhand item value  " + item.getValue());
        }
        Log.e(TAG, "inhand after " + inhand);
        updateDifference();
        notifyDataSetChanged();
        if (demand.size() > 0) setLoadDone(true);
    }


    public void setDemandList(List<HIDBbidrow> demandList) {
        for (String key : hiStockRowList3) {
            demand.put(key, 0);
        }

        Log.e(TAG, "Match Found" + inhand.keySet());
        Log.e(TAG, "Demand Found" + demand.keySet());
        for (HIDBbidrow item : demandList) {
            String key = "RV";
            demand.put(key, demand.get(key) + item.getRVCount());

            key = "PCV";
            demand.put(key, demand.get(key) + item.getPCVCount());
            key = "Measles";
            demand.put(key, demand.get(key) + item.getMeaslesCount());
            key = "DPT";
            demand.put(key, demand.get(key) + item.getDPTCount());
            key = "BCG";
            demand.put(key, demand.get(key) + item.getBCGCount());
            key = "OPV";
            demand.put(key, demand.get(key) + item.getOPVCount());
//            key = "Total";
//            demand.put(key, demand.get(key) + item.getVaccineCount());
        }
        updateDifference();
        Log.e(TAG, "demand after " + demand);

        notifyDataSetChanged();
        if (inhand.size() > 0) setLoadDone(true);
    }

    void updateDifference() {
        for (String key : inhand.keySet()) {
            if (demand.keySet().contains(key)) {
                difference.put(key, inhand.get(key) - demand.get(key));
            }
        }
        Log.e(TAG, "updateDifference: " + difference);
    }

    public void setLoadDone(boolean loadDone) {
        this.loadDone = loadDone;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return TYPE_PROGRESS;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_in_handvsdemand_row_header, parent, false);
            return new HIAdapterStockHeaderHolder(v);
        } else if (viewType == TYPE_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_progress, parent, false);
            return new HIAdapterStockFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_in_handvsdemand_row, parent, false);
            return new HIAdapterStockReportHolder(v, parent.getContext());
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HIAdapterStockHeaderHolder) {

        } else if (holder instanceof HIAdapterStockFooterHolder) {
            HIAdapterStockFooterHolder viewHolder = (HIAdapterStockFooterHolder) holder;
            if (!loadDone) viewHolder.vProgressBar.setVisibility(View.VISIBLE);
            else viewHolder.vProgressBar.setVisibility(View.GONE);
        } else if (holder instanceof HIAdapterStockReportHolder) {
            String currentItem = getItem(position - 1);
            HIAdapterStockReportHolder viewHolder = (HIAdapterStockReportHolder) holder;
            viewHolder.tvOrder.setText(String.valueOf(position));
            viewHolder.tvName.setText(currentItem);
            viewHolder.tvInhand.setText(String.valueOf(inhand.get(currentItem)));
            viewHolder.tvDemand.setText(String.valueOf(demand.get(currentItem)));
            viewHolder.tvDifference.setText(String.valueOf(difference.get(currentItem)));
            if (difference.get(currentItem) != null && difference.get(currentItem) <= 0) {
                viewHolder.tvDifference.setBackgroundColor(Color.RED);
            } else {
                viewHolder.tvDifference.setBackgroundColor(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        return hiStockRowList3.size() + 2;
    }

    private String getItem(int position) {
        return hiStockRowList3.get(position);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == hiStockRowList3.size() + 1;
    }


    class HIAdapterStockHeaderHolder extends RecyclerView.ViewHolder {

        public HIAdapterStockHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterStockReportHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvOrder)
        TextView tvOrder;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvInhand)
        TextView tvInhand;
        @Bind(R.id.tvDemand)
        TextView tvDemand;
        @Bind(R.id.tvDifference)
        TextView tvDifference;

        Context context;

        public HIAdapterStockReportHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }
    }

    class HIAdapterStockFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vProgressBar)
        ProgressBar vProgressBar;
        Context context;

        public HIAdapterStockFooterHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

    }
}
