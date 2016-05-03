package org.hispindia.bidtrackerreports.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow4;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sourabh on 1/25/16.
 */
public class HIAdapterStockReport4 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static String TAG = HIAdapterStockReport4.class.getSimpleName();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROGRESS = 1;
    private static final int TYPE_HEADER = 2;

    private boolean loadDone;
    private List<HIStockRow4> hiStockRowList4;


    public HIAdapterStockReport4() {
        hiStockRowList4 = new ArrayList<>();
        loadDone = false;
    }



    public void setHiStockRowList4(List<HIStockRow4> hiStockRowList4) {
        this.hiStockRowList4 = hiStockRowList4;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_row_header, parent, false);
            return new HIAdapterStockHeaderHolder(v);
        } else if (viewType == TYPE_PROGRESS) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_progress, parent, false);
            return new HIAdapterStockFooterHolder(v, parent.getContext());
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hiviewitem_stock_row, parent, false);
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
            HIStockRow4 currentItem = getItem(position - 1);
            HIAdapterStockReportHolder viewHolder = (HIAdapterStockReportHolder) holder;
            viewHolder.tvOrder.setText(String.valueOf(currentItem.getOrder()));
            viewHolder.tvName.setText(currentItem.getName());
            viewHolder.tvValue.setText(currentItem.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return hiStockRowList4.size() + 2;
    }

    private HIStockRow4 getItem(int position) {
        return hiStockRowList4.get(position);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == hiStockRowList4.size() + 1;
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
        @Bind(R.id.tvValue)
        TextView tvValue;
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
