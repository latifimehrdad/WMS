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

    @SerializedName("fromDeliverDate")
    Date fromDeliverDate;

    @SerializedName("toDeliverDate")
    Date toDeliverDate;

    @SerializedName("requestCode")
    String requestCode;

    @SerializedName("totalAmount")
    float totalAmount;

    @SerializedName("wasteAmountEstimates")
    List<String> wasteAmountEstimates;

    @SerializedName("booth")
    MD_Booth booth;

    @SerializedName("wasteAmountRequests")
    List<MD_WasteAmountRequests2> wasteAmountRequests;


    public MD_ItemWasteRequest(ArrayList<ModelMessage> messages, Byte wasteCollectionState, Byte deliveryType, Date requestDate, Date fromDeliverDate, Date toDeliverDate, String requestCode, float totalAmount, List<String> wasteAmountEstimates, MD_Booth booth, List<MD_WasteAmountRequests2> wasteAmountRequests) {
        super(messages);
        this.wasteCollectionState = wasteCollectionState;
        this.deliveryType = deliveryType;
        this.requestDate = requestDate;
        this.fromDeliverDate = fromDeliverDate;
        this.toDeliverDate = toDeliverDate;
        this.requestCode = requestCode;
        this.totalAmount = totalAmount;
        this.wasteAmountEstimates = wasteAmountEstimates;
        this.booth = booth;
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

    public List<String> getWasteAmountEstimates() {
        return wasteAmountEstimates;
    }

    public void setWasteAmountEstimates(List<String> wasteAmountEstimates) {
        this.wasteAmountEstimates = wasteAmountEstimates;
    }

    public List<MD_WasteAmountRequests2> getWasteAmountRequests() {
        return wasteAmountRequests;
    }

    public void setWasteAmountRequests(List<MD_WasteAmountRequests2> wasteAmountRequests) {
        this.wasteAmountRequests = wasteAmountRequests;
    }

    public MD_Booth getBooth() {
        return booth;
    }

    public void setBooth(MD_Booth booth) {
        this.booth = booth;
    }

    public Date getFromDeliverDate() {
        return fromDeliverDate;
    }

    public void setFromDeliverDate(Date fromDeliverDate) {
        this.fromDeliverDate = fromDeliverDate;
    }

    public Date getToDeliverDate() {
        return toDeliverDate;
    }

    public void setToDeliverDate(Date toDeliverDate) {
        this.toDeliverDate = toDeliverDate;
    }
}
