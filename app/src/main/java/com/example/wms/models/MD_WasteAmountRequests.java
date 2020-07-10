package com.example.wms.models;

import com.example.wms.database.DB_ItemsWasteList;

import java.util.List;

public class MD_WasteAmountRequests {

    Integer DeliveryType;

    MD_Booth Booth;

    ModelTime TimeSheet;

    List<MD_RequestCollect> WasteAmountRequests;

    public MD_WasteAmountRequests(Integer deliveryType, MD_Booth booth, ModelTime timeSheet, List<MD_RequestCollect> wasteAmountRequests) {
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

    public List<MD_RequestCollect> getWasteAmountRequests() {
        return WasteAmountRequests;
    }

    public void setWasteAmountRequests(List<MD_RequestCollect> wasteAmountRequests) {
        WasteAmountRequests = wasteAmountRequests;
    }
}
