package giphyimage.gifmos.avick.com.giphyimage.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import giphyimage.gifmos.avick.com.giphyimage.Model.DataModel;

/**
 * Created by avick on 11/4/16.
 */

public class BasicUtils {

    static DataModel data;


    public static void setData(DataModel item) {
        data = item;
    }

    public static DataModel getData() {
        return data;
    }

    public static void saveSearchQuery(String query, Context context) {
        ArrayList<String> queryList;
        queryList = getSearchQueries(context);
//        if(queryList.size() > 0 ) {

        if(queryList.contains(query)) {
            queryList.remove(query);
        }

        if (queryList.size() == 5) {
            queryList.remove(4);

        }


        queryList.add(0, query);

        for(int i = 0; i < queryList.size(); i++ ) {
            putSearchQuery(context, i+5-queryList.size(), queryList.get(i));
        }


//        ArrayList<String> queryList;
//        Set<String> set = new LinkedHashSet<>();
//        queryList = getSearchQueries(context);
//        if(queryList.size() > 0 ) {
//
//            if(queryList.size() == 5) {
//                queryList.remove(0);
//
//            }
//            queryList.add(query);
//            for(String q : queryList) {
//                ((LinkedHashSet)set).add(q);
//            }
//            //set.addAll(queryList);
//        } else {
////            queryList = new ArrayList<>();
////            queryList.add(query);
//            ((LinkedHashSet)set).add(query);
//            //set.addAll(queryList);
//        }

//        PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(Constants.RECENT_SEARCH_QUERY, set ).commit();
    }


    public static ArrayList<String> getSearchQueries(Context context) {
        ArrayList<String> queryList = new ArrayList<>();
        int counter = 5;
        String query;
        query = getSearchQuery(context, counter-1);
        while (counter != 0 &&  query!= null ) {
            queryList.add(0, query);
            counter--;
            query = getSearchQuery(context, counter-1);
        }

        return queryList;
//        Set<String> set;
//        set = new LinkedHashSet<String>(PreferenceManager.getDefaultSharedPreferences(context).getStringSet(Constants.RECENT_SEARCH_QUERY, new LinkedHashSet<String>()));
//        if(!set.isEmpty()) {
//            return new ArrayList<>(set);
//
//        } else {
//            return null;
//        }
    }

    public static String getSearchQuery(Context context, int key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Integer.toString(key), null);
    }

    public static void putSearchQuery(Context context, int key, String query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Integer.toString(key), query ).commit();
    }



}
