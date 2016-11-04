package giphyimage.gifmos.avick.com.giphyimage.Model;

import android.content.Intent;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pagination {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("offset")
    @Expose
    private Integer offset;


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * 
     * @param offset
     *     The offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
