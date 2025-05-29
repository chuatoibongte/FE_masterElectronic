package com.example.mater_electronic.models.account;

import java.util.List;

public class UpdateAddressRequest {
    private List<Address> addressList;

    public UpdateAddressRequest(List<Address> addressList) {
        this.addressList = addressList;
    }

    // Getters and setters
    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
