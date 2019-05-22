package com.vertilogic.paul.vertipos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vertilogic.paul.vertipos.R;
import com.vertilogic.paul.vertipos.model.ItemModel;
import com.vertilogic.paul.vertipos.view.MainActivityViewCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {
    private List<ItemModel> modelList = new ArrayList<>();
    private List<ItemModel> modelListFiltered = new ArrayList<>();
    private String[] dataQuantity = null;
    private Context context;
    private MainActivityViewCallBack mainActivityViewCallBack;

    public ItemAdapter(Context context, MainActivityViewCallBack mainActivityViewCallBack) {
        this.context = context;
        this.mainActivityViewCallBack = mainActivityViewCallBack;
    }

    public List<ItemModel> getModelList() {
        return modelListFiltered;
    }

    public void setModelList(List<ItemModel> modelList) {
        this.modelList = modelList;
        this.modelListFiltered = modelList;
        dataQuantity = new String[modelListFiltered.size()];

        Log.d("Data size", "Data size: " + dataQuantity.length);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cv_item_and_price, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        ItemModel model = getModelList().get(i);

        Glide.with(context)
                .load(model.getPicture())
                .fitCenter()
                .into(itemViewHolder.imgItem);

        int price = model.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        itemViewHolder.edtQuantity.setText(dataQuantity[i]);
        itemViewHolder.tvItemName.setText(model.getItem());
        itemViewHolder.tvItemPrice.setText((context.getString(R.string.rp) + decimalFormat.format(price)));
    }

    @Override
    public int getItemCount() {
        Log.d("List size", "size: " + modelListFiltered.size());
        return modelListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    modelListFiltered = modelList;
                } else {
                    List<ItemModel> filteredList = new ArrayList<>();

                    for (ItemModel model : modelList) {
                        if (model.getItem().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(model);
                        }
                    }

                    modelListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = modelListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                modelListFiltered = (ArrayList<ItemModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_items_and_price) CardView cvItem;
        @BindView(R.id.img_item) ImageView imgItem;
        @BindView(R.id.tv_item_name) TextView tvItemName;
        @BindView(R.id.tv_item_price) TextView tvItemPrice;
        @BindView(R.id.edt_quantity) EditText edtQuantity;
        @BindView(R.id.btn_add_item) ImageButton btnAddItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtQuantity.getText().toString().matches("^0")) {
                        edtQuantity.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    dataQuantity[getAdapterPosition()] = s.toString();
                }
            });

            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemModel model = modelListFiltered.get(getAdapterPosition());

                    if (!edtQuantity.getText().toString().isEmpty() ) {
                        int itemQuantity = Integer.parseInt(edtQuantity.getText().toString());
                        int itemPrice = model.getPrice();
                        int discountRate = model.getDiscount();

                        if (itemQuantity == 0) {
                            return;
                        }

                        int priceBeforeDiscount = itemQuantity * itemPrice;
                        int priceDiscount = ((discountRate/100) * priceBeforeDiscount);
                        int priceAfterDiscount = priceBeforeDiscount - priceDiscount;

                        model.setQuantity(itemQuantity);
                        model.setPriceBeforeDiscount(priceBeforeDiscount);
                        model.setPriceDiscount(priceDiscount);
                        model.setTotalPrice(priceAfterDiscount);

                        if (mainActivityViewCallBack != null) {
                            mainActivityViewCallBack.showTransaction(model);
                        }
                    }
                }
            });
        }
    }
}
