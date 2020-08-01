package com.ngra.wms.models;

import java.util.List;

public class MD_WasteAmountRequests {

    Integer DeliveryType;

    MD_Booth Booth;

    ModelTime TimeSheet;

    MD_WasteEstimate WasteEstimate;


    public MD_WasteAmountRequests(Integer deliveryType, MD_Booth booth, ModelTime timeSheet, MD_WasteEstimate wasteEstimate) {
        DeliveryType = deliveryType;
        Booth = booth;
        TimeSheet = timeSheet;
        WasteEstimate = wasteEstimate;
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

    public ModelTime getTimeSheet() {
        return TimeSheet;
    }

    public void setTimeSheet(ModelTime timeSheet) {
        TimeSheet = timeSheet;
    }


    public MD_WasteEstimate getWasteEstimate() {
        return WasteEstimate;
    }

    public void setWasteEstimate(MD_WasteEstimate wasteEstimate) {
        WasteEstimate = wasteEstimate;
    }
}
