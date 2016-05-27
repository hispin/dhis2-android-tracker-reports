package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.db.HIDBbidrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nhancao on 1/25/16.
 */
public class HIAdapterDemandOnlyReport3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int abc = 0;
    public final static String TAG = HIAdapterDemandOnlyReport3.class.getSimpleName();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;
    private static final int TYPE_HEADER = 2;
    public List<String> hiStockRowList;

    public HashMap<String, Integer> demand;


    private boolean loadDone;

    public HIAdapterDemandOnlyReport3() {
        hiStockRowList = new ArrayList<>();

        demand = new HashMap<>();


        hiStockRowList.add("RV");
        hiStockRowList.add("PCV");
        hiStockRowList.add("Measles");
        hiStockRowList.add("DPT");
        hiStockRowList.add("BCG");
        hiStockRowList.add("OPV");
        // hiStockRowList.add("Total");
        loadDone = false;
    }




    public void setDemandList(List<HIDBbidrow> demandList) {
        for (String key : hiStockRowList) {
            demand.put(key, 0);
        }


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

        Log.e(TAG, "demand after " + demand);

        notifyDataSetChanged();

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_in_demand_row_header, parent, false);
            return new HIAdapterStockHeaderHolder(v);
        } else if (viewType == TYPE_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_progress, parent, false);
            return new HIAdapterStockFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_in_demand_row, parent, false);
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

            viewHolder.tvDemand.setText(String.valueOf(demand.get(currentItem)));


        }
    }

    @Override
    public int getItemCount() {
        return hiStockRowList.size() + 2;
    }

    private String getItem(int position) {
        return hiStockRowList.get(position);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == hiStockRowList.size() + 1;
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

        @Bind(R.id.tvDemand)
        TextView tvDemand;


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
