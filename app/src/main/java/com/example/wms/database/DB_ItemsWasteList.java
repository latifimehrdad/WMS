package com.example.wms.database;

import io.realm.RealmObject;

public class DB_ItemsWasteList extends RealmObject {

    Integer Id;

    String Name;

    Integer Count;

    public void insert(Integer id, String name, Integer count) {
        Id = id;
        Name = name;
        Count = count;
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

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }
}
