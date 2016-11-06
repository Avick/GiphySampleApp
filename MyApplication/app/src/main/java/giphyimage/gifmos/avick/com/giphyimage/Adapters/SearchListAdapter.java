package giphyimage.gifmos.avick.com.giphyimage.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import giphyimage.gifmos.avick.com.giphyimage.Model.DataModel;
import giphyimage.gifmos.avick.com.giphyimage.R;

/**
 * Created by avick on 11/3/16.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class VIEW_TYPES{
        public static final int ItemType = 1;
        public static final int ProgessType = -1;
    }

    ArrayList<DataModel> mDataset;
    Context mContext;
    OnItemClickListener mListener;
    Fragment fragment;

    public SearchListAdapter(ArrayList<DataModel> mDataset, Context context, OnItemClickListener mListener, Fragment fragment) {
        this.mDataset = mDataset;
        this.mContext = context;
        this.mListener = mListener;
        this.fragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPES.ItemType) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
            return new ViewHolderGiphyImage(v);
        } else if(viewType == VIEW_TYPES.ProgessType) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_progress_bar, parent, false);
            return new ProgressViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderGiphyImage) {
            ViewHolderGiphyImage mHolder = (ViewHolderGiphyImage)holder;
            updateDisplay(mDataset.get(position).getImages().getFixedHeightSmall().getUrl(),mHolder.gifImageView, mHolder.gifProgressBar);
        }else {
            ProgressViewHolder mHolder = (ProgressViewHolder) holder;
            mHolder.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(mDataset != null){
            return mDataset.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position){
        if(mDataset.get(position) != null){
            return VIEW_TYPES.ItemType;
        } else {
            return VIEW_TYPES.ProgessType;
        }
    }


    public class ViewHolderGiphyImage extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView gifImageView;
        public ProgressBar gifProgressBar;
        public ViewHolderGiphyImage(View itemView) {
            super(itemView);
            gifImageView = (ImageView) itemView.findViewById(R.id.glide_gif);
            gifProgressBar = (ProgressBar) itemView.findViewById(R.id.gif_progress_bar);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if( getAdapterPosition() >= 0 && mDataset.get(getAdapterPosition()) != null) {
                mListener.onItemClick(mDataset.get(getAdapterPosition()));
            }
        }
    }



    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.recycler_progressbar);
        }
    }

    private void updateDisplay(String url, ImageView imageView, final ProgressBar progressBar) {

        Glide.with(fragment)
                .load(url)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .error(R.color.greyish_black)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.GONE);
                        //check isRefreshing
                    }
                });
    }


    public interface OnItemClickListener{
        void onItemClick(DataModel data);
    }
}
