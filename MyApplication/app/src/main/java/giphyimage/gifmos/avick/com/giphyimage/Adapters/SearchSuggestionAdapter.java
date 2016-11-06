package giphyimage.gifmos.avick.com.giphyimage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import giphyimage.gifmos.avick.com.giphyimage.R;

/**
 * Created by avick on 11/5/16.
 */

public class SearchSuggestionAdapter extends ArrayAdapter<String> {

    ArrayList<String> mDataset;
    Context context;


    public SearchSuggestionAdapter(Context context, ArrayList<String> mDataset) {
        super(context, R.layout.search_suggestion_item, mDataset);
        this.context = context;
        this.mDataset = mDataset;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.search_suggestion_item, parent, false);

        TextView txtRecentSearchItem = (TextView) rowView.findViewById(R.id.txt_search_term);
        txtRecentSearchItem.setText(mDataset.get(position));

        return rowView;
    }
}
