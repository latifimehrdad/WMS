package com.ngra.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MD_ItemWasteRequest extends ModelResponsePrimary{

    @SerializedName("wasteCollectionState")
    Byte wasteCollectionState;

    @SerializedName("deliveryType")
    Byte deliveryType;

    @SerializedName("requestDate")
    Date requestDate;

    @SerializedName("requestCode")
    String requestCode;

    @SerializedName("totalAmount")
    float totalAmount;

    @SerializedName("wasteAmountRequests")
    List<MR_Collect> wasteAmountRequests;


    public MD_ItemWasteRequest(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public MD_ItemWasteRequest(ArrayList<ModelMessage> messages, Byte wasteCollectionState, Byte deliveryType, Date requestDate, String requestCode, float totalAmount, List<MR_Collect> wasteAmountRequests) {
        super(messages);
        this.wasteCollectionState = wasteCollectionState;
        this.deliveryType = deliveryType;
        this.requestDate = requestDate;
        this.requestCode = requestCode;
        this.totalAmount = totalAmount;
        this.wasteAmountRequests = wasteAmountRequests;
    }

    public Byte getWasteCollectionState() {
        return wasteCollectionState;
    }

    public void setWasteCollectionState(Byte wasteCollectionState) {
        this.wasteCollectionState = wasteCollectionState;
    }

    public Byte getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<MR_Collect> getWasteAmountRequests() {
        return wasteAmountRequests;
    }

    public void setWasteAmountRequests(List<MR_Collect> wasteAmountRequests) {
        this.wasteAmountRequests = wasteAmountRequests;
    }
}
