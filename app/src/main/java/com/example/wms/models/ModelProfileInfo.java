package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelProfileInfo extends ModelResponsePrimary {

    @SerializedName("result")
    ModelProfile result;

    public ModelProfileInfo(ArrayList<ModelMessage> messages) {
        super(messages);
    }

    public class ModelProfile {

        @SerializedName("firstName")
        String firstName;

        @SerializedName("lastName")
        String lastName;

        @SerializedName("gender")
        Integer gender;

        @SerializedName("citizenType")
        Integer citizenType;

        @SerializedName("referenceCode")
        Integer referenceCode;

        @SerializedName("province")
        ModelSpinnerItem province;

        @SerializedName("city")
        ModelSpinnerItem city;

        @SerializedName("neighbourhood")
        ModelSpinnerItem neighbourhood;


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getCitizenType() {
            return citizenType;
        }

        public void setCitizenType(Integer citizenType) {
            this.citizenType = citizenType;
        }

        public Integer getReferenceCode() {
            return referenceCode;
        }

        public void setReferenceCode(Integer referenceCode) {
            this.referenceCode = referenceCode;
        }

        public ModelSpinnerItem getProvince() {
            return province;
        }

        public void setProvince(ModelSpinnerItem province) {
            this.province = province;
        }

        public ModelSpinnerItem getCity() {
            return city;
        }

        public void setCity(ModelSpinnerItem city) {
            this.city = city;
        }

        public ModelSpinnerItem getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(ModelSpinnerItem neighbourhood) {
            this.neighbourhood = neighbourhood;
        }
    }

    public ModelProfile getResult() {
        return result;
    }

    public void setResult(ModelProfile result) {
        this.result = result;
    }
}
