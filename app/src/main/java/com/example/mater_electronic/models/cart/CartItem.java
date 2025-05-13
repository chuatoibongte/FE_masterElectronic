package com.example.mater_electronic.models.cart;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
        private String img;
        private String name;
        private double price;
        private int quantity;
        private String mainCategory;

    public CartItem(String img, String name, double price, int quantity, String mainCategory) {
            this.img = img;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.mainCategory = mainCategory;
        }

    public CartItem(String name, double price, int quantity) {
            this.img = "";
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.mainCategory = "";
        }

    // ✅ Bổ sung constructor để đọc từ Parcel
    protected CartItem(Parcel in) {
        img = in.readString();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        mainCategory = in.readString();
    }

    // ✅ Triển khai Parcelable
    public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {
       @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

       @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
       }
    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
        dest.writeString(mainCategory);
    }


    public int describeContents() {
    return 0;
   }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
}
