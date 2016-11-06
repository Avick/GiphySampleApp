package giphyimage.gifmos.avick.com.giphyimage.Network;


import giphyimage.gifmos.avick.com.giphyimage.Model.GiphyApiResultModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import static giphyimage.gifmos.avick.com.giphyimage.Network.GiphyServices.GET_RESULT;

/**
 * Created by avick on 10/25/16.
 */

public interface GiphyServices {

    String GET_RESULT = "/v1/gifs/search";
    String GET_TRENDING = "/v1/gifs/trending";

    @GET(GET_RESULT)
    void getResult(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, @Query("api_key") String api_key, Callback<GiphyApiResultModel> callback);

    @GET(GET_TRENDING)
    void getTrending(@Query("offset") int offset, @Query("limit") int limit, @Query("api_key") String api_key, Callback<GiphyApiResultModel> callback);

}
