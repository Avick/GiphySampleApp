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
//    String GET_CUISINES = "/cuisines";
//    String SEARCH = "/search";
//
//
//    @GET(GET_CUISINES)
//    void getCuisines(@Query("lat") String lat, @Query("lon") String lon, Callback<> callback);
//
//    @GET(SEARCH)
//    void getTrendingResult(@Query("lat") String lat, @Query("lon") String lon, @Query("cuisines") String cuisines, @Query("count") String count, @Query("q") String q, Callback<SearchResultModel> callback);

    @GET(GET_RESULT)
    void getResult(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit, @Query("api_key") String api_key, Callback<GiphyApiResultModel> callback);

    @GET(GET_TRENDING)
    void getTrending(@Query("offset") int offset, @Query("limit") int limit, @Query("api_key") String api_key, Callback<GiphyApiResultModel> callback);

    //void getResults();
}
