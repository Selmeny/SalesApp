package com.vertilogic.paul.vertipos.view;

import com.vertilogic.paul.vertipos.model.ItemModel;
import com.vertilogic.paul.vertipos.model.TransactionModel;

public interface MainActivityViewCallBack {
    void showTransaction(ItemModel model);
    void calculateTotalPrice(int price);
    void settleTransaction(TransactionModel model);
}
