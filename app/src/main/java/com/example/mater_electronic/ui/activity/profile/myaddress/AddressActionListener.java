package com.example.mater_electronic.ui.activity.profile.myaddress;

import com.example.mater_electronic.models.account.Address;

public interface AddressActionListener {
    void onDeleteAddress(int position);
    void onEditAddress(int position, Address address);
}