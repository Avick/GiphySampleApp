package giphyimage.gifmos.avick.com.giphyimage.Network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import giphyimage.gifmos.avick.com.giphyimage.Utils.Constants;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by avick on 10/25/16.
 */

public class NetworkDAO {

    private static NetworkDAO instance;
    private final GiphyServices services;
    private static Context mContext;



    private NetworkDAO(String endpoint, Context mContext) {
        this.mContext = mContext;
        Gson gson = new GsonBuilder().disableHtmlEscaping()
                .create();
        RestAdapter.Builder networkAdapterBuilder = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setRequestInterceptor(getRequestInterceptor())
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);
        services = networkAdapterBuilder.build().create(GiphyServices.class);
    }

    private static RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

//                CommonNetworkHeaders headers = CommonNetworkHeaders.getInstance();
                request.addHeader(NetworkConstants.API_KEY, Constants.PUBLIC_BETA_KEY);
            }
        };
    }

    public static NetworkDAO getInstance(String endpoint, Context mContext) {
        if (instance == null) {
            instance = new NetworkDAO(endpoint, mContext);
        }
        return instance;
    }


    public void getSearchResult(String q, int offset) {
        services.getResult(q, offset, 26, Constants.PUBLIC_BETA_KEY, NetworkRequestListener.getInstance());
    }

    public void getTreading(int limit) {
        services.getTrending(0, limit, Constants.PUBLIC_BETA_KEY, NetworkRequestListener.getInstance());
    }

}
