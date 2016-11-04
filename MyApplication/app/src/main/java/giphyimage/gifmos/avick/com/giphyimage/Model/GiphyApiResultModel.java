package giphyimage.gifmos.avick.com.giphyimage.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avick on 11/3/16.
 */

public class GiphyApiResultModel extends BaseVO {
    @SerializedName("data")
    @Expose
    private List<DataModel> data = new ArrayList<DataModel>();
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    /**
     *
     * @return
     *     The data
     */
    public List<DataModel> getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(List<DataModel> data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     *     The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     *     The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     *
     * @param pagination
     *     The pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
