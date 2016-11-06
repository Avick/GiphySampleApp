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
        dialog = super.onCreateDialog(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);



        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setLayout((metrics.widthPixels), (metrics.heightPixels - (int) (metrics.heightPixels * 0.6)));
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL| Gravity.TOP);
        WindowManager.LayoutParams param = getDialog().getWindow().getAttributes();
        param.y = positionY;
        getDialog().getWindow().setAttributes(param);


    }

}
