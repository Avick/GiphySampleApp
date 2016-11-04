package giphyimage.gifmos.avick.com.giphyimage.Model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import giphyimage.gifmos.avick.com.giphyimage.Utils.Constants;

/**
 * Created by avick on 11/3/16.
 */

public class ErrorVO {


    private String Tag;

    @Expose
    private List<String> errors = new ArrayList<String>();

    @Expose
    private int statusCode = 0;

    /**
     * @return The errors
     */
    public String getErrors() {
        String error = Constants.GENERIC_ERROR;
        if (errors != null) {
            StringBuilder builder = new StringBuilder();
            for (String str : errors) {
                builder.append(str);
                builder.append(". ");
            }
            error = builder.toString();
        }
        return error;
    }

    /**
     * @param errors The errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
