package com.vertilogic.paul.vertipos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.vertilogic.paul.vertipos.R;
import com.vertilogic.paul.vertipos.adapter.ItemAdapter;
import com.vertilogic.paul.vertipos.adapter.TransactionAdapter;
import com.vertilogic.paul.vertipos.dummy_data.DummyItems;
import com.vertilogic.paul.vertipos.model.ItemModel;
import com.vertilogic.paul.vertipos.model.TransactionModel;
import com.vertilogic.paul.vertipos.view.MainActivityViewCallBack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vertilogic.paul.vertipos.activities.SettleTransactionActivity.TRANSACTION_KEY;

public class MainActivity extends AppCompatActivity implements MainActivityViewCallBack {
    public static final String USER_NAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private List<ItemModel> listItem = new ArrayList<>();
    private List<ItemModel> listTransaction = new ArrayList<>();
    public static final String[] itemCategories = {"All price", "Low price", "High price"};

    private ItemAdapter itemAdapter;
    private TransactionAdapter transactionAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.sv_search_item) SearchView svItems;
    @BindView(R.id.spn_item_category) Spinner spnItemCategory;
    @BindView(R.id.rv_items_and_price) RecyclerView rvItems;
    @BindView(R.id.btn_customer) Button btnCustomer;
    @BindView(R.id.rv_transaction) RecyclerView rvTransaction;
    @BindView(R.id.tv_price_rupiah) TextView tvPriceRupiah;
    @BindView(R.id.tv_discount_rupiah) TextView tvDiscountRateRupiah;
    @BindView(R.id.tv_tax_rupiah) TextView tvTaxRateRupiah;
    @BindView(R.id.tv_total_price_rupiah) TextView tvTotalPriceRupiah;
    @BindView(R.id.btn_settle_trn) Button btnSettleTrans;
    @BindView(R.id.btn_hold_bill) Button btnHoldBill;
    @BindView(R.id.btn_clear_transaction) Button btnClearTrans;
    @BindView(R.id.btn_transaction_history) Button btnTransHistory;
    @BindView(R.id.btn_setting) Button btnSetting;
    @BindView(R.id.btn_open_cash) Button btnOpenCash;
    @BindView(R.id.btn_end_of_day) Button btnEndOfDay;
    @BindView(R.id.btn_log_out) Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initiateAdapters();

        svItems.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemAdapter.getFilter().filter(query);
                svItems.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                itemAdapter.getFilter().filter(query);

                return false;
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, itemCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnItemCategory.setAdapter(adapter);

        spnItemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int category = parent.getSelectedItemPosition();
                List<ItemModel> categories = new ArrayList<>();

                switch (category) {
                    case 0:
                        showItems();
                        break;

                    case 1:
                        for (ItemModel model : listItem) {
                            if (model.getCategory().equals(itemCategories[1])) {
                                categories.add(model);
                                Log.d("Low Price", "Item added" + model.getItem());
                            }
                        }
                        itemAdapter.setModelList(categories);
                        itemAdapter.notifyDataSetChanged();
                        break;

                    case 2:
                        for (ItemModel model : listItem) {
                            if (model.getCategory().equals(itemCategories[2])) {
                                categories.add(model);
                                Log.d("High Price", "Item added" + model.getItem());
                            }
                        }
                        itemAdapter.setModelList(categories);
                        itemAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSettleTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionAdapter.recordTransaction();
            }
        });
    }

    public void initiateAdapters() {
        itemAdapter = new ItemAdapter(this, this);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(itemAdapter);
        rvItems.setHasFixedSize(true);

        transactionAdapter = new TransactionAdapter(this, this);
        rvTransaction.setLayoutManager(new LinearLayoutManager(this));
        rvTransaction.setAdapter(transactionAdapter);
        rvTransaction.setHasFixedSize(true);
    }

    public void showItems() {
        listItem = DummyItems.getDummyItems();
        itemAdapter.setModelList(listItem);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTransaction(ItemModel model) {
        listTransaction.add(model);
        transactionAdapter.setModelList(listTransaction);
        transactionAdapter.notifyDataSetChanged();
        transactionAdapter.getTotalPrice();
    }

    @Override
    public void calculateTotalPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvPriceRupiah.setText((this.getResources().getString(R.string.rp)
                + decimalFormat.format(price)));
        tvTotalPriceRupiah.setText((this.getResources().getString(R.string.rp)
                + decimalFormat.format(price)));
    }

    @Override
    public void settleTransaction(TransactionModel model) {
        Intent intent = new Intent(MainActivity.this, SettleTransactionActivity.class);
        intent.putExtra(TRANSACTION_KEY, model);
        startActivity(intent);
    }
}