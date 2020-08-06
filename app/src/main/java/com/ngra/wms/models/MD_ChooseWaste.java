package com.ngra.wms.models;

public class MD_ChooseWaste {

    Integer Id;

    String Name;

    Integer Amount;

    public MD_ChooseWaste(Integer id, String name, Integer amount) {
        Id = id;
        Name = name;
        Amount = amount;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }
}
