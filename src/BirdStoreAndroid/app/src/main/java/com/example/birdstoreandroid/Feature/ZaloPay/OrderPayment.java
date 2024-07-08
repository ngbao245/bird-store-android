package com.example.birdstoreandroid.Feature.ZaloPay;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstoreandroid.Feature.ZaloPay.Api.CreateOrder;
import com.example.birdstoreandroid.R;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderPayment extends AppCompatActivity {
    TextView txtTongTien;
    Button btnThanhToan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);

        txtTongTien = findViewById(R.id.textViewTongTien);
        btnThanhToan = findViewById(R.id.buttonThanhToan);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Intent intent = getIntent();

        double total = 10000;
        String totalString = String.format("%.0f", total);
        txtTongTien.setText(Double.toString(total));

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(OrderPayment.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String transactionId, String s1, String s2) {
                                //change here
                                Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Thanh toán thành công");
                                intent1.putExtra("transactionId", transactionId);
                                Log.d("GGGGGG", "Thanh toán thành công");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Hủy thanh toán");
                                Log.d("GGGGGG", "Hủy thanh toán");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                                intent1.putExtra("result", "Lỗi thanh toán");
                                Log.d("GGGGGG", "Lỗi thanh toán");
                                startActivity(intent1);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}