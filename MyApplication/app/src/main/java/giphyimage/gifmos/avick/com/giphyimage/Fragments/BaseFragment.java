package giphyimage.gifmos.avick.com.giphyimage.Fragments;

import android.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import de.greenrobot.event.EventBus;
import giphyimage.gifmos.avick.com.giphyimage.R;


/**
 * Created by avick on 10/24/16.
 */


public class BaseFragment extends Fragment {

    View containerView;


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public View addToBaseFragment(View view) {
        View baseView = View.inflate(getActivity(), R.layout.base_fragment, null);
        containerView = baseView.findViewById(R.id.lnr_base_fragment_container);
        ((RelativeLayout) containerView).addView(view);
        return baseView;
    }

    public void showFragmentProgressBar() {
        if (getView() != null) {
            View pbView = getView().findViewById(R.id.pb_loading_indicator_1);
            if (pbView != null) {
                pbView.setVisibility(View.VISIBLE);
            } else {
                throw new RuntimeException("DIMWIT! Include the progress_fragment_layout.xml in your fragment view");
            }
        }
    }

    public void hideFragmentProgressBar() {
        if (getView() != null) {
            View pbView = getView().findViewById(R.id.pb_loading_indicator_1);
            if (pbView != null) {
                pbView.setVisibility(View.GONE);
            }
        }
    }


    public  void onEvent(Object o) {

    }


}
