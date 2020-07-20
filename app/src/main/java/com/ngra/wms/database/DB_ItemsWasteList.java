package com.ngra.wms.database;

import io.realm.RealmObject;

public class DB_ItemsWasteList extends RealmObject {

    Integer Id;

    String Name;

    Integer Amount;

    public void insert(Integer id, String name, Integer amount) {
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
