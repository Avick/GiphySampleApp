package giphyimage.gifmos.avick.com.giphyimage.Utils;

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
}
