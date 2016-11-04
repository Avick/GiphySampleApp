package giphyimage.gifmos.avick.com.giphyimage.Activities;

import android.app.Fragment;
import android.os.Bundle;

import giphyimage.gifmos.avick.com.giphyimage.Fragments.TrendingFragment;
import giphyimage.gifmos.avick.com.giphyimage.R;

/**
 * Created by avick on 11/3/16.
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragmenttoContainer();
    }

    private void addFragmenttoContainer() {
        Fragment fragment = TrendingFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.lnr_data_container, fragment, TrendingFragment.TAG).addToBackStack(null).commit();
    }

}
