package giphyimage.gifmos.avick.com.giphyimage.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import giphyimage.gifmos.avick.com.giphyimage.Activities.BaseActivity;
import giphyimage.gifmos.avick.com.giphyimage.Adapters.SearchListAdapter;
import giphyimage.gifmos.avick.com.giphyimage.Adapters.SearchSuggestionAdapter;
import giphyimage.gifmos.avick.com.giphyimage.Model.DataModel;
import giphyimage.gifmos.avick.com.giphyimage.Model.ErrorVO;
import giphyimage.gifmos.avick.com.giphyimage.Model.GiphyApiResultModel;
import giphyimage.gifmos.avick.com.giphyimage.Network.NetworkConstants;
import giphyimage.gifmos.avick.com.giphyimage.Network.NetworkDAO;
import giphyimage.gifmos.avick.com.giphyimage.R;
import giphyimage.gifmos.avick.com.giphyimage.Utils.BasicUtils;
import giphyimage.gifmos.avick.com.giphyimage.Utils.Constants;
import giphyimage.gifmos.avick.com.giphyimage.Utils.EndlessRecyclerOnScrollListener;

/**
 * Created by avick on 11/3/16.
 */

public class SearchFragment extends BaseFragment implements SearchListAdapter.OnItemClickListener {

    RecyclerView mRecyclerView;
    TextView txtEmptyState, txtLayoutHeading;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    ArrayList<DataModel> mDataSet;
    int counter = 0;
    SearchListAdapter mAdapter;
    GridLayoutManager mLayoutManager;
    String q;
    int totalCount = 0;
    boolean hasNextPage = false;
    public final static String TAG = SearchFragment.class.getSimpleName();
    DialogFragment dialogFragment;
    PopupWindow popup;

    public static SearchFragment newInstance() {
        SearchFragment frag = new SearchFragment();
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
        if (mDataSet != null && mDataSet.size() > 0) {
            mAdapter = new SearchListAdapter(mDataSet, getActivity(), this);
            mRecyclerView.setAdapter(mAdapter);
            if (q != null) {
                txtEmptyState.setVisibility(View.GONE);
                txtLayoutHeading.setText(getString(R.string.search_layout_header_text, totalCount, q));
                txtLayoutHeading.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            txtEmptyState.setText(getString(R.string.no_result));
            txtEmptyState.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            txtLayoutHeading.setVisibility(View.GONE);
        }

    }


    @Override
    public void onItemClick(DataModel data) {
        BasicUtils.setData(data);
        dismissPopup();
        hideKeyBoard(((BaseActivity)getActivity()).getSearchText());
        getFragmentManager().beginTransaction()
                //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .setCustomAnimations(R.animator.enter_right, R.animator.exit_left, R.animator.enter_left, R.animator.exit_right)
                .replace(R.id.lnr_data_container, ShowGifFragment.newInstance(), ShowGifFragment.TAG).addToBackStack(null).commit();
    }

    public void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_search_result);
        txtLayoutHeading = (TextView) view.findViewById(R.id.txt_trending_header);
        txtLayoutHeading.setVisibility(View.GONE);
//        txtLayoutHeading.setText(getActivity().getString(R.string.));
        txtEmptyState = (TextView) view.findViewById(R.id.empty_state);
        txtEmptyState.setText(getString(R.string.search_command));
        txtEmptyState.setVisibility(View.VISIBLE);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int count) {
                counter++;
                getSearchResult(counter);
                mDataSet.add(null);
                if (mAdapter != null) {
                    mAdapter.notifyItemChanged(mDataSet.size() - 1);
                    //mAdapter.notifyItemChanged((int) Math.ceil(((double) mDataSet.size())/2));
                }
            }
        };
        mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);


    }


    public void onEvent(GiphyApiResultModel result) {
        if (result.getTag() != null && result.getTag().equals(Constants.SEARCH_RESULT)) {
            hideFragmentProgressBar();
            updateRecyclerView(result);
        }
    }


    public void onEvent(ErrorVO errorVO) {
        hideFragmentProgressBar();


        if (mDataSet.size() > 0 && mDataSet.get(mDataSet.size() - 1) == null) {
            mDataSet.remove(mDataSet.size() - 1);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        } else if (mDataSet == null || mDataSet.size() == 0) {
            txtEmptyState.setText(getString(R.string.no_result));
            mRecyclerView.setVisibility(View.GONE);
            txtEmptyState.setVisibility(View.VISIBLE);
        }
    }

    public void getSearchResult(int offset) {
        NetworkDAO.getInstance(NetworkConstants.API_ENDPOINT, getActivity()).getSearchResult(q, offset);
    }


    public void updateRecyclerView(GiphyApiResultModel result) {
        if (mDataSet == null) {
            mDataSet = new ArrayList<>();
        }

        if (mDataSet.size() > 0 && mDataSet.get(mDataSet.size() - 1) == null) {
            mDataSet.remove(mDataSet.size() - 1);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }

        if (result != null && result.getData() != null && result.getData().size() > 0) {

            if (result.getPagination().getTotalCount() > (counter + 1) * result.getPagination().getCount()) {
                hasNextPage = true;
            } else {
                hasNextPage = false;
            }

            mDataSet.addAll(result.getData());
            if (mAdapter == null) {
                totalCount = result.getPagination().getTotalCount();
                txtLayoutHeading.setText(getString(R.string.search_layout_header_text, totalCount, q));
                txtLayoutHeading.setVisibility(View.VISIBLE);
                mAdapter = new SearchListAdapter(mDataSet, getActivity(), this);
                mRecyclerView.setAdapter(mAdapter);
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            mAdapter.notifyDataSetChanged();


        } else if (result != null && result.getData() != null && result.getData().size() == 0) {
            if (mDataSet == null || mDataSet.size() == 0) {
                mRecyclerView.setVisibility(View.GONE);
                txtEmptyState.setText(getString(R.string.no_result));
                txtEmptyState.setVisibility(View.VISIBLE);
            }
        }


        if (hasNextPage) {
            endlessRecyclerOnScrollListener.setLoading(false);
        }


        if (counter == 0) {
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mAdapter != null && position >= 0) {
                        switch (mAdapter.getItemViewType(position)) {
                            case SearchListAdapter.VIEW_TYPES.ItemType:
                                return 1;
                            case SearchListAdapter.VIEW_TYPES.ProgessType:
                                return 2; //number of columns of the grid
                            default:
                                return -1;
                        }
                    } else {
                        return -1;
                    }
                }

            });
        }


    }


    public void customizeToolBar() {

        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View focusedView = ((BaseActivity) getActivity()).getCurrentFocus();
                if (view != null) {
                    dismissPopup();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((((BaseActivity)getActivity()).getSearchText()).getWindowToken(), 0);

                }
                getActivity().onBackPressed();

            }

        };

        ((BaseActivity) getActivity()).showBackButton(mListener);
        ((BaseActivity) getActivity()).getTxtHeaderView().setVisibility(View.GONE);
        ((BaseActivity) getActivity()).getSearchText().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getImgSearch().setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).getSearchText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        ((BaseActivity) getActivity()).getSearchText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.hasFocus()) {
                    int[] location = new int[2];
                    //((BaseActivity)getActivity()).getSearchText().getLocationInWindow(location);
                    ((BaseActivity) getActivity()).getActionBarToolBar().getLocationOnScreen(location);
                    location[1] += ((BaseActivity) getActivity()).getActionBarToolBar().getHeight();
                    //showDialog(location[1]);
                    showPopup(view, location);
                }
            }
        });

        ((BaseActivity) getActivity()).getImgSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerSearch(((BaseActivity) getActivity()).getSearchText());
            }
        });

        //                if ((((BaseActivity) getActivity()).getSearchText().toString().equals("")) || (((BaseActivity) getActivity()).getSearchText().getText().toString().equals(q))) {
//                    if (((BaseActivity) getActivity()).getSearchText().getText().toString().equals("")) {
//                        q = null;
//                        txtEmptyState.setText(getString(R.string.search_command));
//                        mRecyclerView.setVisibility(View.GONE);
//                        txtEmptyState.setVisibility(View.VISIBLE);
//                        txtLayoutHeading.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    txtEmptyState.setVisibility(View.GONE);
//                    mRecyclerView.setVisibility(View.GONE);
//                    q = ((BaseActivity) getActivity()).getSearchText().getText().toString();
//                    BasicUtils.saveSearchQuery(q, getActivity());
//                    startSearch();
//                }
//
//                hideKeyBoard(((BaseActivity) getActivity()).getSearchText());


        ((BaseActivity) getActivity()).getSearchText().setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                            ((BaseActivity) getActivity()).getSearchText().clearFocus();
                            ((BaseActivity) getActivity()).getImgSearch().requestFocus();
                            triggerSearch(v);
                            return true;
                        }
                        return false;
                    }
                });
    }


    public void startSearch() {

        mDataSet = null;
        if (mAdapter != null) {
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.DefaultSpanSizeLookup());
//            mLayoutManager.requestLayout();
//            mAdapter.notifyDataSetChanged();
        }
        mAdapter = null;
        counter = 0;
        showFragmentProgressBar();
        getSearchResult(counter);
    }

    public void triggerSearch(TextView editText) {
        dismissPopup();
        if ((editText.getText().toString().equals("")) || (editText.getText().toString().equals(q))) {
            if (editText.getText().toString().equals("")) {
                txtEmptyState.setText(getString(R.string.search_command));
                mRecyclerView.setVisibility(View.GONE);
                txtEmptyState.setVisibility(View.VISIBLE);
                txtLayoutHeading.setVisibility(View.GONE);
            }

        } else {
            txtEmptyState.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            q = editText.getText().toString();
            BasicUtils.saveSearchQuery(q, getActivity());
            startSearch();
        }

        hideKeyBoard(editText);
    }

    public void hideKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }



    public void showPopup(View v, int[] location) {
        final ArrayList<String> queries = BasicUtils.getSearchQueries(getActivity());
        if (queries.size() > 0) {
            if (popup == null) {
                popup = new PopupWindow(getActivity());
            }
            View layout = getActivity().getLayoutInflater().inflate(R.layout.search_suggestion_layout, null);
            ListView mListView = (ListView) layout.findViewById(R.id.lv_search_history);
            SearchSuggestionAdapter mAdapter = new SearchSuggestionAdapter(getActivity(), queries);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((BaseActivity) getActivity()).getSearchText().setText(queries.get(position));
                    ((BaseActivity) getActivity()).getSearchText().setSelection(queries.get(position).length());
                    ((BaseActivity) getActivity()).getSearchText().clearFocus();
                    ((BaseActivity) getActivity()).getImgSearch().requestFocus();
                    triggerSearch(((BaseActivity) getActivity()).getSearchText());
                }
            });
            popup.setContentView(layout);
            // Set content width and height
            popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            popup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);


            // Closes the popup window when touch outside of it - when looses focus
            //popup.setOutsideTouchable(true);
            //popup.setFocusable(true);

            // Show anchored to button
            popup.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.search_suggestion_background)); //.getColor(android.R.color.white)
            popup.showAtLocation(layout, Gravity.NO_GRAVITY, location[0], location[1]);
            //popup.showAsDropDown(anchorView);
        }

    }

    public void dismissPopup() {
        if (popup != null) {
            popup.dismiss();
        }
    }
}
