package com.vertilogic.paul.vertipos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TransactionModel implements Parcelable {
    private String paymentMethod, receiptNumber, transactionDate, transactionTime, creditCardName;
    private List<ItemModel> transactionItem;
    private int sumPrice, creditCardNumber, DebitCardNumber;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public List<ItemModel> getTransactionItem() {
        return transactionItem;
    }

    public void setTransactionItem(List<ItemModel> transactionItem) {
        this.transactionItem = transactionItem;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public TransactionModel() {
    }

    public int getDebitCardNumber() {
        return DebitCardNumber;
    }

    public void setDebitCardNumber(int debitCardNumber) {
        DebitCardNumber = debitCardNumber;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentMethod);
        dest.writeString(this.receiptNumber);
        dest.writeString(this.transactionDate);
        dest.writeString(this.transactionTime);
        dest.writeString(this.creditCardName);
        dest.writeList(this.transactionItem);
        dest.writeInt(this.sumPrice);
        dest.writeInt(this.creditCardNumber);
        dest.writeInt(this.DebitCardNumber);
    }

    protected TransactionModel(Parcel in) {
        this.paymentMethod = in.readString();
        this.receiptNumber = in.readString();
        this.transactionDate = in.readString();
        this.transactionTime = in.readString();
        this.creditCardName = in.readString();
        this.transactionItem = new ArrayList<ItemModel>();
        in.readList(this.transactionItem, ItemModel.class.getClassLoader());
        this.sumPrice = in.readInt();
        this.creditCardNumber = in.readInt();
        this.DebitCardNumber = in.readInt();
    }

    public static final Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel source) {
            return new TransactionModel(source);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };
}
