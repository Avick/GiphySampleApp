package giphyimage.gifmos.avick.com.giphyimage.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import giphyimage.gifmos.avick.com.giphyimage.Activities.BaseActivity;
import giphyimage.gifmos.avick.com.giphyimage.Adapters.SearchListAdapter;
import giphyimage.gifmos.avick.com.giphyimage.Model.DataModel;
import giphyimage.gifmos.avick.com.giphyimage.R;
import giphyimage.gifmos.avick.com.giphyimage.Utils.BasicUtils;

/**
 * Created by avick on 11/4/16.
 */

public class ShowGifFragment extends BaseFragment {

    public final static String TAG = ShowGifFragment.class.getSimpleName();

    DataModel data;
    TextView txtGifView;
    ImageView imgGifView;
    ProgressBar pbShowGif;

    public static ShowGifFragment newInstance() {
        ShowGifFragment frag = new ShowGifFragment();
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_gif_layout, container, false);
        customizeToolBar();
        init(view);

        //view.setVisibility(View.GONE);
        //return addToBaseFragment(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void init(View view) {
        imgGifView = (ImageView) view.findViewById(R.id.img_show_gif);
        txtGifView = (TextView) view.findViewById(R.id.txt_show_gif);
        pbShowGif = (ProgressBar) view.findViewById(R.id.pb_show_gif);
        if(BasicUtils.getData() != null) {
            data = BasicUtils.getData();
            BasicUtils.setData(null);
            txtGifView.setText("Source: "+data.getSource());
            updateDisplay(data.getImages().getDownsizedLarge().getUrl(), imgGifView, pbShowGif);
        }
    }

    public void customizeToolBar(){
        ((BaseActivity)getActivity()).showBackButton();
        ((BaseActivity)getActivity()).getTxtHeaderView().setText(getString(R.string.gif_image_details));
        ((BaseActivity)getActivity()).getSearchText().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getTxtHeaderView().setVisibility(View.VISIBLE);
    }

    private void updateDisplay(String url, ImageView imageView, final ProgressBar progressBar) {

//        Log.i(TAG, "updateDisplay GIF URL: " + gifUrl);

        Glide.with(getActivity())
                .load(url)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.GONE);
                        //check isRefreshing
                    }
                });
    }
}
