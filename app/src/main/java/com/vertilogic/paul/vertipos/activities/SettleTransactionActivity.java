package com.vertilogic.paul.vertipos.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.vertilogic.paul.vertipos.R;
import com.vertilogic.paul.vertipos.model.TransactionModel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class SettleTransactionActivity extends AppCompatActivity {
    public static final String TRANSACTION_KEY = "transaction_key";

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19;
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16;
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5;
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1;
    private static final char CARD_NUMBER_DIVIDER = '-';

    private TransactionModel transactionModel;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cl_payment_method_cash) ConstraintLayout clPaymentMethodCash;
    @BindView(R.id.cl_payment_method_credit) ConstraintLayout clPaymentMethodCredit;
    @BindView(R.id.cl_payment_method_debit) ConstraintLayout clPaymentMethodDebit;
    @BindView(R.id.tv_settle_price) TextView tvSettlePrice;
    @BindView(R.id.cb_cash) CheckBox cbCash;
    @BindView(R.id.cb_credit_card) CheckBox cbCreditCard;
    @BindView(R.id.cb_debit_card) CheckBox cbDebitCard;
    @BindView(R.id.tv_amount_due_result) TextView tvAmountDueResult;
    @BindView(R.id.edt_paid_result) EditText edtPaidResult;
    @BindView(R.id.btn_payment_method_cash_charge) Button btnPaymentMethodCash;
    @BindView(R.id.tv_amount_due_credit_result) TextView tvAmountDueCreditResult;
    //@BindView(R.id.edt_credit_card_number) EditText edtCreditCardNumber;
    @BindView(R.id.edt_credit_card_name) EditText edtCreditCardName;
    @BindView(R.id.btn_payment_method_credit_charge) Button btnPaymentMethodCredit;
    @BindView(R.id.tv_amount_due_debit_result) TextView tvAmountDueDebitResult;
    @BindView(R.id.edt_debit_card_number) EditText edtDebitCardNumber;
    @BindView(R.id.btn_payment_method_debit_charge) Button btnPaymentMethodDebit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_transaction);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Settle transaction");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        onCheckboxClicked(cbCash);
        showAmountDue();
    }

    @OnTextChanged(value = R.id.edt_credit_card_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void onCardNumberTextChanged(Editable s) {
        try {
            if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO
                    , CARD_NUMBER_DIVIDER)) {
                s.replace(0, s.length(), concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS)
                        , CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.cb_cash:
                if (checked) {
                    clPaymentMethodCash.setVisibility(View.VISIBLE);
                    cbDebitCard.setChecked(false);
                    cbCreditCard.setChecked(false);
                    clPaymentMethodCredit.setVisibility(View.GONE);
                    clPaymentMethodDebit.setVisibility(View.GONE);
                    edtPaidResult.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            initiateCustomDecimalFormat(edtPaidResult, this, s);
                        }
                    });
                    btnPaymentMethodCash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {
                    clPaymentMethodCash.setVisibility(View.GONE);
                }
                break;

            case R.id.cb_credit_card:
                if (checked) {
                    clPaymentMethodCredit.setVisibility(View.VISIBLE);
                    cbCash.setChecked(false);
                    cbDebitCard.setChecked(false);
                    clPaymentMethodCash.setVisibility(View.GONE);
                    clPaymentMethodDebit.setVisibility(View.GONE);
                    /*
                    edtCreditCardNumber.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    */
                } else {
                    clPaymentMethodCredit.setVisibility(View.GONE);
                }
                break;

            case R.id.cb_debit_card:
                if (checked) {
                    clPaymentMethodDebit.setVisibility(View.VISIBLE);
                    cbCash.setChecked(false);
                    cbCreditCard.setChecked(false);
                    clPaymentMethodCash.setVisibility(View.GONE);
                    clPaymentMethodCredit.setVisibility(View.GONE);
                    edtDebitCardNumber.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    clPaymentMethodDebit.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void showAmountDue() {
        transactionModel = getIntent().getParcelableExtra(TRANSACTION_KEY);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvSettlePrice.setText((this.getResources().getString(R.string.rp)
                + " "
                + decimalFormat.format(transactionModel.getSumPrice())));
        tvAmountDueResult.setText(decimalFormat.format(transactionModel.getSumPrice()));
        tvAmountDueCreditResult.setText(decimalFormat.format(transactionModel.getSumPrice()));
        tvAmountDueDebitResult.setText(decimalFormat.format(transactionModel.getSumPrice()));
    }

    private void initiateCustomDecimalFormat(EditText editText, TextWatcher textWatcher, Editable editable) {
        editText.removeTextChangedListener(textWatcher);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        try {
            int initialLength = editText.getText().length();
            String text = editable.toString().replace(String.valueOf(decimalFormat
                    .getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number displayNumber = decimalFormat.parse(text);
            int initialSelection = editText.getSelectionStart();
            editText.setText(decimalFormat.format(displayNumber));

            int finalLength = editText.getText().length();
            int selection = (initialSelection + (initialLength - finalLength));

            if (selection > 0 && selection <= editText.getText().length()) {
                editText.setSelection(selection);
            } else {
                editText.setSelection(editText.getText().length() - 1);
            }

        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
        }

        editText.addTextChangedListener(textWatcher);
    }

    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }

        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }

        return digits;
    }
}
