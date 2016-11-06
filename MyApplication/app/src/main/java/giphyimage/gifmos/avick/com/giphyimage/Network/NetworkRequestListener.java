package giphyimage.gifmos.avick.com.giphyimage.Network;

import android.util.Log;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import giphyimage.gifmos.avick.com.giphyimage.Utils.Constants;
import giphyimage.gifmos.avick.com.giphyimage.Model.BaseVO;
import giphyimage.gifmos.avick.com.giphyimage.Model.ErrorVO;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by avick on 10/26/16.
 */

public class NetworkRequestListener implements Callback {

    private static NetworkRequestListener instance;

    public static NetworkRequestListener getInstance() {
        if (instance == null) {
            instance = new NetworkRequestListener();
        }
        return instance;
    }

    @Override
    public void success(Object o, Response response) {
        String endpoint = response.getUrl();
        String TAG = null;
        if (endpoint.contains(NetworkConstants.API_ENDPOINT + GiphyServices.GET_RESULT)) {
            //case NetworkConstants.API_ENDPOINT + GiphyServices.GET_RESULT:
            TAG = Constants.SEARCH_RESULT;
        } else if (endpoint.contains(NetworkConstants.API_ENDPOINT + GiphyServices.GET_TRENDING)){
            TAG = Constants.TRENDING;

        }

        if(TAG != null) {
            ((BaseVO) o).setTag(TAG);
        }
        EventBus.getDefault().post(o);
    }

    @Override
    public void failure(RetrofitError error) {

        Log.e("RequestFailure", "RetrofitError = " + error.getMessage());
        ErrorVO errorVO = null;
        if (error.getResponse() != null) {
            try {
                errorVO = (ErrorVO) error.getBodyAs(ErrorVO.class);
                errorVO.setStatusCode(error.getResponse().getStatus());
            } catch (Exception e) {
                Log.e("RequestFailure", "No error message found. Response: " + error.getResponse());
            }
        }



        if (errorVO == null) {
            errorVO = new ErrorVO();
            ArrayList<String> errorList = new ArrayList<>(1);
            errorList.add(Constants.GENERIC_ERROR);
            errorVO.setErrors(errorList);
        }

        EventBus.getDefault().post(errorVO);
    }

}
