package com.aztechcorps.comedy.standapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.aztechcorps.comedy.standapp.Misc.ChannelCardDetail;
import com.aztechcorps.comedy.standapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;


public class ChannelCardAdapter extends RecyclerView.Adapter<ChannelCardAdapter.GridItemViewHolder> {
    private Context context;
    private List<ChannelCardDetail> mItemList;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    /**
     * Called when a grid adapter has been called
     *
     * @param context   The context of main activity
     * @param mItemList Detail List of recyclerview grid that contains data
     */
    public ChannelCardAdapter(Context context, List<ChannelCardDetail> mItemList) {
        this.context = context;
        this.mItemList = mItemList;
    }

    @Override
    public GridItemViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_item_list, parent, false);
        return new GridItemViewHolder(itemView, this);
    }


    @Override
    public void onBindViewHolder(GridItemViewHolder holder, int position) {
        ChannelCardDetail items = mItemList.get(position);
        holder.mTitle.setText(items.getTitle());
        holder.mCountry.setText(items.getCountry());
        Glide.with(context).load(items.getThumbnail())
                .thumbnail(0.5f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mThumbnail);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * Called when a grid item has been called
     *
     * @param onItemClickListener The view that was clicked.
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(GridItemViewHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }


    public class GridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle, mCountry;
        public ImageView mThumbnail;
        public ChannelCardAdapter mAdapter;

        public GridItemViewHolder(View itemView, ChannelCardAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
            mCountry = (TextView) itemView.findViewById(R.id.item_country);
            mThumbnail = (ImageView) itemView.findViewById(R.id.item_thumbnail);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }
    }
}