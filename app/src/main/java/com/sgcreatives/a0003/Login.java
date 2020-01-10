package com.sgcreatives.a0003;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msg91.sendotpandroid.library.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotpandroid.library.PhoneNumberUtils;

public class Login extends AppCompatActivity {
    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_COUNTRY_CODE = "code";
    private EditText mPhoneNumber;
    private Button mSmsButton;
    final int RequestPermissionCode=1;
    private String mCountryIso;
    private TextWatcher mNumberTextWatcher;
ImageView whatsapp,facebook;
    EditText phoneno;
    TextView phone;
    TextView logo;

    SharedPreferences sh;
    public boolean shlogin=false;
    LinearLayout new_user_layout;
    CardView login_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh = getSharedPreferences("reg", MODE_PRIVATE);
        shlogin = sh.getBoolean("hi", false);

        if (shlogin) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        EnableRuntimePermission();
        tryAndPrefillPhoneNumber();
        phone = findViewById(R.id.phtxt);
        phoneno = findViewById(R.id.uph);
//        password_text = findViewById(R.id.password_text);
        whatsapp = findViewById(R.id.whatsapp_btn);
        facebook = findViewById(R.id.facebook_btn);
        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card);


    }





    private void tryAndPrefillPhoneNumber() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            phoneno.setText(manager.getLine1Number());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }



    private void openActivity(String phoneNumber) {
        Intent verification = new Intent(this, otp.class);
        verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
        verification.putExtra(INTENT_COUNTRY_CODE, "+91");
        startActivity(verification);
    }

    private void setButtonsEnabled(boolean enabled) {
        login_card.setEnabled(enabled);
    }

    public void onButtonClicked(View view) {
        openActivity(getE164Number());
    }

    private void resetNumberTextWatcher(String countryIso) {

        if (mNumberTextWatcher != null) {
            phoneno.removeTextChangedListener(mNumberTextWatcher);
        }


        mNumberTextWatcher = new PhoneNumberFormattingTextWatcher(countryIso) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public synchronized void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (isPossiblePhoneNumber()) {
                    setButtonsEnabled(true);
                    phoneno.setTextColor(Color.BLACK);
                } else {
                    setButtonsEnabled(false);
                    phoneno.setTextColor(Color.RED);
                }
            }
        };

        phoneno.addTextChangedListener(mNumberTextWatcher);
    }

    private boolean isPossiblePhoneNumber() {
        return PhoneNumberUtils.isPossibleNumber(phoneno.getText().toString(),"+91");
    }

    private String getE164Number() {
        return phoneno.getText().toString().replaceAll("\\D", "").trim();
        // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
    }
    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this,
                Manifest.permission.READ_SMS)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Login.this, new String[]{
                    Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.RECEIVE_SMS}, RequestPermissionCode);


        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

// Toast.makeText(Cpature_image.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                   // Toast.makeText(this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }

    }

}
