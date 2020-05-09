package com.senoritasaudi.senoritaprovider.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicRequestsCountResponseModel {

    @SerializedName("finished")
    @Expose
    private Integer finished;
    @SerializedName("waiting")
    @Expose
    private Integer waiting;
    @SerializedName("cancel")
    @Expose
    private Integer cancel;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
