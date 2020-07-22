package com.ngra.wms.models;

import java.util.List;

public class MD_WasteAmountRequests {

    Integer DeliveryType;

    MD_Booth Booth;

    ModelTime TimeSheet;

    List<MR_Collect> WasteAmountRequests;

    public MD_WasteAmountRequests(Integer deliveryType, MD_Booth booth, ModelTime timeSheet, List<MR_Collect> wasteAmountRequests) {
        DeliveryType = deliveryType;
        Booth = booth;
        TimeSheet = timeSheet;
        WasteAmountRequests = wasteAmountRequests;
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

    public List<MR_Collect> getWasteAmountRequests() {
        return WasteAmountRequests;
    }

    public void setWasteAmountRequests(List<MR_Collect> wasteAmountRequests) {
        WasteAmountRequests = wasteAmountRequests;
    }
}
