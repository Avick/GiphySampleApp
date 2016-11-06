package giphyimage.gifmos.avick.com.giphyimage.Network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by avick on 10/26/16.
 */

public class CommonNetworkHeaders implements Parcelable {

    private static CommonNetworkHeaders singleInstance;
    private static final String COMMON_API_HEADERS = "commonHeaders";

    private int apiKey;

    private CommonNetworkHeaders() {

    }

    public static CommonNetworkHeaders getInstance() {
        if (singleInstance == null) {
            singleInstance = new CommonNetworkHeaders();
        }

        return singleInstance;
    }

    public int getApiKey() {
        return apiKey;
    }

    public void setApiKey(int apiKey) {
        this.apiKey = apiKey;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.apiKey);
    }

    protected CommonNetworkHeaders(Parcel in) {
        this.apiKey = in.readInt();
    }

    public static final Creator<CommonNetworkHeaders> CREATOR = new Creator<CommonNetworkHeaders>() {
        @Override
        public CommonNetworkHeaders createFromParcel(Parcel source) {
            return new CommonNetworkHeaders(source);
        }

        @Override
        public CommonNetworkHeaders[] newArray(int size) {
            return new CommonNetworkHeaders[size];
        }
    };
}
