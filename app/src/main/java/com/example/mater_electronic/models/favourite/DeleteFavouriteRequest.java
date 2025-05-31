package com.example.mater_electronic.models.favourite;

public class DeleteFavouriteRequest {
    private String electronicID;

    public DeleteFavouriteRequest(String electronicID) {
        this.electronicID = electronicID;
    }

    public String getElectronicID() {
        return electronicID;
    }

    public void setElectronicID(String electronicID) {
        this.electronicID = electronicID;
    }
}
