package com.vertilogic.paul.vertipos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vertilogic.paul.vertipos.R;
import com.vertilogic.paul.vertipos.model.ItemModel;
import com.vertilogic.paul.vertipos.model.TransactionModel;
import com.vertilogic.paul.vertipos.view.MainActivityViewCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<ItemModel> modelList = new ArrayList<>();
    private Context context;
    private MainActivityViewCallBack mainActivityViewCallBack;

    public TransactionAdapter(Context context, MainActivityViewCallBack mainActivityViewCallBack) {
        this.context = context;
        this.mainActivityViewCallBack = mainActivityViewCallBack;
        notifyDataSetChanged();
    }

    public List<ItemModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<ItemModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cv_detail_transaction, viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i) {
        ItemModel model = getModelList().get(i);

        String itemName = model.getItem();
        int itemPrice = model.getPrice();
        int itemQuantity = model.getQuantity();
        int discountRate = model.getDiscount();
        int priceBeforeDiscount = model.getPriceBeforeDiscount();
        int priceDiscount = model.getPriceDiscount();
        int priceAfterDiscount = model.getTotalPrice();

        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        transactionViewHolder.tvDetailTransactionItemName.setText(itemName);
        transactionViewHolder.tvDetailTransactionItemPrice
                .setText((String.valueOf(itemQuantity)
                        + " "
                        + context.getString(R.string.multiplier)
                        + " "
                        + context.getString(R.string.rp)
                        + decimalFormat.format(itemPrice)));
        transactionViewHolder.tvDetailTransactionItemPriceResult
                .setText((context.getString(R.string.rp)
                        + decimalFormat.format(priceBeforeDiscount)));
        transactionViewHolder.tvDetailTransactionItemDiscount
                .setText((context.getString(R.string.discount_2)
                        + " "
                        + String.valueOf(discountRate)
                        + context.getString(R.string.percent)));
        transactionViewHolder.tvDetailTransactionItemDiscountResult
                .setText((context.getString(R.string.rp)
                        + decimalFormat.format(priceDiscount)));
        transactionViewHolder.tvDetailTransactionPrice
                .setText((context.getString(R.string.rp)
                        + decimalFormat.format(priceAfterDiscount)));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_detail_transaction) CardView cvDetailTransaction;
        @BindView(R.id.tv_detail_transaction_item_name) TextView tvDetailTransactionItemName;
        @BindView(R.id.tv_detail_transaction_item_price) TextView tvDetailTransactionItemPrice;
        @BindView(R.id.tv_detail_transaction_item_price_result) TextView tvDetailTransactionItemPriceResult;
        @BindView(R.id.tv_detail_transaction_item_discount) TextView tvDetailTransactionItemDiscount;
        @BindView(R.id.tv_detail_transaction_item_discount_result) TextView tvDetailTransactionItemDiscountResult;
        @BindView(R.id.tv_detail_transaction_price) TextView tvDetailTransactionPrice;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvDetailTransaction.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    modelList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    getTotalPrice();

                    return true;
                }
            });
        }
    }

    private int sumPrice() {
        int price = 0;
        for (ItemModel model : modelList) {
            price += model.getTotalPrice();
        }

        return price;
    }

    public void getTotalPrice() {
        if (modelList != null) {
            mainActivityViewCallBack.calculateTotalPrice(sumPrice());
        }
    }

    public void recordTransaction() {
        if (modelList != null) {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setTransactionItem(modelList);
            transactionModel.setSumPrice(sumPrice());
            mainActivityViewCallBack.settleTransaction(transactionModel);
        }
    }

}
