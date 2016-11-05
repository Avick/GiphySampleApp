package giphyimage.gifmos.avick.com.giphyimage.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import giphyimage.gifmos.avick.com.giphyimage.Activities.BaseActivity;
import giphyimage.gifmos.avick.com.giphyimage.Adapters.SearchListAdapter;
import giphyimage.gifmos.avick.com.giphyimage.Model.DataModel;
import giphyimage.gifmos.avick.com.giphyimage.Model.ErrorVO;
import giphyimage.gifmos.avick.com.giphyimage.Model.GiphyApiResultModel;
import giphyimage.gifmos.avick.com.giphyimage.Network.NetworkConstants;
import giphyimage.gifmos.avick.com.giphyimage.Network.NetworkDAO;
import giphyimage.gifmos.avick.com.giphyimage.Utils.BasicUtils;
import giphyimage.gifmos.avick.com.giphyimage.Utils.Constants;
import giphyimage.gifmos.avick.com.giphyimage.Utils.EndlessRecyclerOnScrollListener;
import giphyimage.gifmos.avick.com.giphyimage.R;

/**
 * Created by avick on 11/3/16.
 */

public class TrendingFragment extends BaseFragment implements SearchListAdapter.OnItemClickListener{

    RecyclerView mRecyclerView;
    TextView txtEmptyState, txtTrending;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    ArrayList<DataModel>mDataSet;
    int counter = 0;
    SearchListAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    public final static String TAG = TrendingFragment.class.getSimpleName();

    public static TrendingFragment newInstance() {
        TrendingFragment frag = new TrendingFragment();
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result, container, false);
        customizeToolBar();
        init(view);

        //view.setVisibility(View.GONE);
        //return addToBaseFragment(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mDataSet == null) {
            counter = 0;
            showFragmentProgressBar();
            getTrendingResult(26);
        } else if( mDataSet != null) {
            mAdapter = new SearchListAdapter(mDataSet, getActivity(),this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    @Override
    public void onItemClick(DataModel data) {
        BasicUtils.setData(data);
        getFragmentManager().beginTransaction()
                //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .setCustomAnimations(R.animator.enter_right, R.animator.exit_left, R.animator.enter_left, R.animator.exit_right)
                .replace(R.id.lnr_data_container, ShowGifFragment.newInstance(), ShowGifFragment.TAG).addToBackStack(null).commit();
    }

    public void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_search_result);
        txtTrending = (TextView) view.findViewById(R.id.txt_trending_header);
        txtEmptyState = (TextView) view.findViewById(R.id.empty_state);
        mLayoutManager = new GridLayoutManager(getActivity(),2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }



    public void onEvent(GiphyApiResultModel result) {
        if(result.getTag()!= null && result.getTag().equals(Constants.TRENDING)) {
            hideFragmentProgressBar();
            updateRecyclerView(result);
        }
    }


    public void onEvent(ErrorVO errorVO) {
        hideFragmentProgressBar();
    }

    public void getTrendingResult(int offset) {
        NetworkDAO.getInstance(NetworkConstants.API_ENDPOINT, getActivity()).getTreading(offset);
    }


    public void updateRecyclerView(GiphyApiResultModel result) {
        if(mDataSet == null) {
            mDataSet = new ArrayList<>();
        }

        if(result != null && result.getData() != null && result.getData().size() > 0) {

            mDataSet.addAll(result.getData());
            if(mAdapter == null) {
                mAdapter = new SearchListAdapter(mDataSet, getActivity(),this);
                mRecyclerView.setAdapter(mAdapter);

            }

            mAdapter.notifyDataSetChanged();


        } else if(result != null && result.getData() != null && result.getData().size() == 0) {
//            counter++;
//            //((BaseActivity)getActivity()).showProgressDialog(null, false);
//            getTrendingResult(counter);
        }

    }

    public void customizeToolBar() {
        ((BaseActivity)getActivity()).hideBackButton();
        ((BaseActivity)getActivity()).getTxtHeaderView().setText(getString(R.string.trending_tool_bar_header));
        ((BaseActivity)getActivity()).getSearchText().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getTxtHeaderView().setVisibility(View.VISIBLE);
        ((BaseActivity)getActivity()).getImgSearch().setVisibility(View.VISIBLE);
        ((BaseActivity)getActivity()).getImgSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .setCustomAnimations(R.animator.enter_right, R.animator.exit_left, R.animator.enter_left, R.animator.exit_right)
                        .replace(R.id.lnr_data_container, SearchFragment.newInstance(), SearchFragment.TAG).addToBackStack(null).commit();
            }
        });
    }
}
