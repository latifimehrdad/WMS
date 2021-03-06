package com.ngra.wms.models;

import java.util.List;

public class MD_WasteAmountRequests {

    private Integer DeliveryType;

    private MD_Booth Booth;

    private MD_Time TimeSheet;

    private List<MD_ChooseWaste> Estimates;

    private MD_SpinnerItem ContactAddress;

    private String app_token;


    public MD_WasteAmountRequests(Integer deliveryType, MD_Booth booth, MD_Time timeSheet, List<MD_ChooseWaste> estimates, MD_SpinnerItem contactAddress, String app_token) {
        DeliveryType = deliveryType;
        Booth = booth;
        TimeSheet = timeSheet;
        Estimates = estimates;
        ContactAddress = contactAddress;
        this.app_token = app_token;
    }

    public Integer getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        DeliveryType = deliveryType;
    }

    public MD_Booth getBooth() {
        return Booth;
    }

    public void setBooth(MD_Booth booth) {
        Booth = booth;
    }

    public MD_Time getTimeSheet() {
        return TimeSheet;
    }

    public void setTimeSheet(MD_Time timeSheet) {
        TimeSheet = timeSheet;
    }

    public List<MD_ChooseWaste> getEstimates() {
        return Estimates;
    }

    public void setEstimates(List<MD_ChooseWaste> estimates) {
        Estimates = estimates;
    }

    public MD_SpinnerItem getContactAddress() {
        return ContactAddress;
    }

    public void setContactAddress(MD_SpinnerItem contactAddress) {
        ContactAddress = contactAddress;
    }

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }
}
