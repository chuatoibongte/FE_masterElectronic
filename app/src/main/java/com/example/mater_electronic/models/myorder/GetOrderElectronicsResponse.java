package com.example.mater_electronic.models.myorder;

import java.util.List;

public class GetOrderElectronicsResponse {
    private boolean success;
    private List<OrderElectronics> electronics;

    public boolean isSuccess() {return success;}
    public List<OrderElectronics> getElectronics() {
        return electronics;
    }
}
