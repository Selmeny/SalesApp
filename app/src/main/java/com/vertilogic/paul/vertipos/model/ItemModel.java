package com.vertilogic.paul.vertipos.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemModel implements Parcelable {
    private String item, picture, category;
    private int price, quantity, discount, priceBeforeDiscount, priceDiscount, totalPrice;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(int priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public int getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(int priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.item);
        dest.writeString(this.picture);
        dest.writeString(this.category);
        dest.writeInt(this.price);
        dest.writeInt(this.quantity);
        dest.writeInt(this.discount);
        dest.writeInt(this.priceBeforeDiscount);
        dest.writeInt(this.priceDiscount);
        dest.writeInt(this.totalPrice);
    }

    public ItemModel() {
    }

    protected ItemModel(Parcel in) {
        this.item = in.readString();
        this.picture = in.readString();
        this.category = in.readString();
        this.price = in.readInt();
        this.quantity = in.readInt();
        this.discount = in.readInt();
        this.priceBeforeDiscount = in.readInt();
        this.priceDiscount = in.readInt();
        this.totalPrice = in.readInt();
    }

    public static final Parcelable.Creator<ItemModel> CREATOR = new Parcelable.Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel source) {
            return new ItemModel(source);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };
}
