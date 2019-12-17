package com.example.wms.models;

import com.google.gson.annotations.SerializedName;

public class ModelSettingInfo extends ModelResponcePrimery {

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
    }
}
