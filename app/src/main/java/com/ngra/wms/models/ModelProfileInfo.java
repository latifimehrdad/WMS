package com.ngra.wms.models;

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
        MD_SpinnerItem province;

        @SerializedName("city")
        MD_SpinnerItem city;

        @SerializedName("neighbourhood")
        MD_SpinnerItem neighbourhood;


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

        public MD_SpinnerItem getProvince() {
            return province;
        }

        public void setProvince(MD_SpinnerItem province) {
            this.province = province;
        }

        public MD_SpinnerItem getCity() {
            return city;
        }

        public void setCity(MD_SpinnerItem city) {
            this.city = city;
        }

        public MD_SpinnerItem getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(MD_SpinnerItem neighbourhood) {
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
