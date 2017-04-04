
package com.andersen.sadwyn.remoteimagesviewer.image;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("children")
    @Expose
    private List<Child_> children = null;
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public List<Child_> getChildren() {
        return children;
    }

    public void setChildren(List<Child_> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

}
