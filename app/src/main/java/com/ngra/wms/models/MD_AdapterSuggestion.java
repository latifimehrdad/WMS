package com.ngra.wms.models;

public class MD_AdapterSuggestion {

    private String address;

    private boolean LoadMore;

    public MD_AdapterSuggestion(String address, boolean loadMore) {
        this.address = address;
        LoadMore = loadMore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isLoadMore() {
        return LoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        LoadMore = loadMore;
    }
}
