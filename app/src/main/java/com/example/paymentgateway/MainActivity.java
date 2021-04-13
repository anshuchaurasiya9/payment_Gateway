package com.example.paymentgateway;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.GmsLogger;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Button payBtn;
    TextView payText;
    private GmsLogger Log;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Checkout.preload(getApplicationContext());

        payBtn = (Button) findViewById(R.id.payBtn);
        payText = (TextView) findViewById(R.id.payText);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makepayment();
            }
        });


    }

    private void makepayment() {


        /**
         * Instantiate Checkout
         */

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_1rO4GYhFp5Hc08");


        checkout.setImage(R.drawable.logo);

        final Activity activity = this;


        //Pass your payment options to the Razorpay Checkout as a JSONObject

        try {
            JSONObject options = new JSONObject();

            options.put("name", "anshu merchant");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//500*100
            options.put("prefill.email", "anshuchaurasiya9@gmail.com");
            options.put("prefill.contact", "8565841359");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        payText.setText("successfull payment ID:" + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        payText.setText("Failed and cause is:" + s);
    }
}
