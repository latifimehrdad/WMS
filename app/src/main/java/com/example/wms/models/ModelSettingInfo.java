package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelSettingInfo extends ModelResponsePrimary {

    @SerializedName("result")
    ModelProfileSetting result;

    public ModelProfileSetting getResult() {
        return result;
    }

    public void setResult(ModelProfileSetting result) {
        this.result = result;
    }

    public class ModelProfileSetting {

        @SerializedName("name")
        String name;

        @SerializedName("lastName")
        String lastName;

        @SerializedName("gender")
        Integer gender;

        @SerializedName("isProfileCompleted")
        Boolean isProfileCompleted;

        @SerializedName("isAddressCompleted")
        Boolean isAddressCompleted;

        @SerializedName("isPackageRequested")
        Boolean isPackageRequested;

        @SerializedName("package")
        ModelPackage modelPackage;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public Boolean getProfileCompleted() {
            return isProfileCompleted;
        }

        public void setProfileCompleted(Boolean profileCompleted) {
            isProfileCompleted = profileCompleted;
        }

        public Boolean getAddressCompleted() {
            return isAddressCompleted;
        }

        public void setAddressCompleted(Boolean addressCompleted) {
            isAddressCompleted = addressCompleted;
        }

        public Boolean getPackageRequested() {
            return isPackageRequested;
        }

        public ModelPackage getModelPackage() {
            return modelPackage;
        }
    }
}
