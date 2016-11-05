package giphyimage.gifmos.avick.com.giphyimage.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import giphyimage.gifmos.avick.com.giphyimage.R;

/**
 * Created by avick on 11/5/16.
 */

public class SearchSuggestionPopup extends DialogFragment {

    final static String DIALOG_POSITION_Y = "dialog_position_y";

    Dialog dialog;
    int positionY;

    public static SearchSuggestionPopup newInstance(int yVal) {
        SearchSuggestionPopup fragment = new SearchSuggestionPopup();
        Bundle bundle = new Bundle();
        bundle.putInt(DIALOG_POSITION_Y, yVal);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positionY = getArguments().getInt(DIALOG_POSITION_Y);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_suggestion_layout, container);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.search_suggestion_layout, null);
//        initViews(dialogLayout);
        dialog = super.onCreateDialog(savedInstanceState);
        //dialog = new AlertDialog.Builder(getActivity()).setView(dialogLayout).create();

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);



        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setLayout(metrics.widthPixels, (metrics.heightPixels - (int) (metrics.heightPixels * 0.7)));//(metrics.widthPixels) (metrics.heightPixels - (int) (metrics.heightPixels * 0.6))
//        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
//        WindowManager.LayoutParams param = new WindowManager.LayoutParams();
//        param.x = 0; param.y = yVal;
        //dialog.getWindow().setAttributes(param);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setLayout((metrics.widthPixels), (metrics.heightPixels - (int) (metrics.heightPixels * 0.6)));
        //getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL| Gravity.TOP);
        WindowManager.LayoutParams param = getDialog().getWindow().getAttributes();
        param.y = positionY;
        getDialog().getWindow().setAttributes(param);


    }



//
//    @Override
//    public void onRemoveSelectedService(ServiceField unSelectService, boolean isPackage) {
//        //clickedServices.remove(unSelectService);
//        selectedServiceAdapter.remove(unSelectService);
//        updateTotalPrice();
//        ZiffiAnalytics.trackSingleValueEvent("CartMenu", "Delete Service", null);
//        //clickedServices.remove(indexOfDataSet(clickedServices, unSelectService));
//
//    }

//    @Override
//    public void onRemoveSelectedOffer(ZiffiOffer unSelectoffer) {
//        selectedServiceAdapter.remove(unSelectoffer);
//        updateTotalPrice();
//    }

    private void initViews(View dialogLayout) {
//        txtSubTotalAmount = (TextView) dialogLayout.findViewById(R.id.txt_sub_total_amount);
//        //cancelBtn = (ImageView) dialogLayout.findViewById(R.id.img_cancel_btn);
//        txtSubTotalAmount.setTypeface(tf_icon);
//        txtSubTotalAmount.setText((char)hexVal+" "+totalServicePrice);
//        doneBtn = (TextView) dialogLayout.findViewById(R.id.txt_dialog_done);
//        doneBtn.setOnClickListener(this);
//        //cancelBtn.setOnClickListener(this);
//        mRecyclerView = (RecyclerView) dialogLayout.findViewById(R.id.selected_service_list_recylcer_view);
//        mRecyclerView.hasFixedSize();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        setRecyclerAdapter();
    }

    public void setRecyclerAdapter() {
//        selectedServiceAdapter = new SelectedServiceAdapter(clickedServices, clickedOffers, getActivity().getBaseContext(), this);
//        mRecyclerView.setAdapter(selectedServiceAdapter);
//        selectedServiceAdapter.notifyDataSetChanged();
//        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//        //itemAnimator.setAddDuration(500);
//        itemAnimator.setRemoveDuration(100);
//        mRecyclerView.setItemAnimator(itemAnimator);
    }
}
