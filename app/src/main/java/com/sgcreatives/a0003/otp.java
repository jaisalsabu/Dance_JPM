package com.sgcreatives.a0003;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.msg91.sendotpandroid.library.SendOTPConfig;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.Verification;
import com.msg91.sendotpandroid.library.VerificationListener;
public class otp extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {

    EditText otpno;
    String phoneNumber;
    TextView otp;
    TextView logo;
    private static final String TAG = Login.class.getSimpleName();
    TextView resend_timer;
    private Verification mVerification;

    LinearLayout new_user_layout;
    CardView login_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendCode();
            }
        });
        startTimer();
        enableInputField(true);
        initiateVerification();
        otpno = findViewById(R.id.inputCode);
        otp = findViewById(R.id.otptxt);
//        password_text = findViewById(R.id.password_text);
//        logo = findViewById(R.id.logo);
//        login_title = findViewById(R.id.login_text);
        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card);



    }

    void createVerification(String phoneNumber, boolean skipPermissionCheck, String countryCode) {
        if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
           // hideProgressBar();
        } else {
            // boolean withoutOtp = false;
            if (NetworkConnectivity.isConnectedMobileNetwork(getApplicationContext())) {
                //   withoutOtp = true;
            } else {

            }
            SendOTPConfig otpConfig = SendOtpVerification
                    .config(countryCode + phoneNumber)
                    .context(this)
                    //////////////////direct verification while connect with mobile network/////////////////////////
                    .autoVerification(true)
                    //////////////////////////////////////////////////////////////////////////////////////////////////
                    .unicode(true) // set true if you want to use unicode (or other language) in sms
                    .expiry("5")//value in minutes
                    .senderId("KANAYA") //where ABCDEF is any string
                    .otplength("6") //length of your otp max length up to 9 digits
                    .message("##OTP## is Your Otp For Registering In KANAYA KALAKSHETRA App. https://www.techsays.in ")
                    //--------case 1-------------------
//                            .message("##OTP## is Your verification digits.")//##OTP## use for default generated OTP
                    //--------case 2-------------------
                    /*  .otp("1234")// Custom Otp code, if want to add yours
                      .message("1234 is Your verification digits.")//Here 1234 same as above Custom otp.*/
                    //-------------------------------------
                    //use single case at a time either 1 or 2
                    .build();
            mVerification = SendOtpVerification.createSmsVerification
                    (otpConfig, this);
            mVerification.initiate();
        }
    }


    /**
     * This work is done  by me rajendra verma
     * if moiblenetwork is true than device is in mobile network
     */
    /*private String getIp(boolean moibleNetwork) {
        if (moibleNetwork) {
            try {

                return IPConverter.getIPAddress(true);
            } catch (Exception ex) {
            }

        } else {
            WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return Formatter.formatIpAddress(ip);
        }
        return "";
    }*/


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
                        + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
                        .show();
            }
            enableInputField(true);
        }
        initiateVerificationAndSuppressPermissionCheck();
    }

    void initiateVerification() {
        initiateVerification(false);
    }

    void initiateVerificationAndSuppressPermissionCheck() {
        initiateVerification(true);
    }

    void initiateVerification(boolean skipPermissionCheck) {
        Intent intent = getIntent();
        if (intent != null) {
             phoneNumber = intent.getStringExtra(Login.INTENT_PHONENUMBER);
            //String countryCode = intent.getStringExtra(MainActivity.INTENT_COUNTRY_CODE);
          /*  TextView phoneText = (TextView) findViewById(R.id.numberText);
            phoneText.setText("+" + "91" + phoneNumber);*/
            createVerification(phoneNumber, skipPermissionCheck, "+91");
        }
    }

    public void ResendCode() {
        startTimer();
        mVerification.resend("voice");
    }

    public void onSubmitClicked(View view) {
        String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
        if (!code.isEmpty()) {
            hideKeypad();
            if (mVerification != null) {
                mVerification.verify(code);
               // showProgress();

                enableInputField(false);
            }
        }
    }

    void enableInputField(boolean enable) {
        View container = findViewById(R.id.inputContainer);
        if (enable) {
            container.setVisibility(View.VISIBLE);
            EditText input = (EditText) findViewById(R.id.inputCode);
            input.requestFocus();
        } else {
            container.setVisibility(View.GONE);
        }
        TextView resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setClickable(false);
    }








    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
       // hideProgressBarAndShowMessage(R.string.failed);
    }

    @Override
    public void onVerified(String response) {
        enableInputField(false);
        Log.d(TAG, "Verified!\n" + response);
        hideKeypad();
        Intent i=new Intent(otp.this,Registration.class);
        i.putExtra("phonevo",phoneNumber);
        startActivity(i);
       // hideProgressBarAndShowMessage(R.string.verified);
//        showCompleted();
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "Verification failed: " + exception.getMessage());
        otpno.setError("Invalid OTP");
        hideKeypad();
       // hideProgressBarAndShowMessage(R.string.failed);
        enableInputField(true);
    }

    private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(otp.this, R.color.red));
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend via call ( " + secondsLeft + " )");
                }
            }
            public void onFinish() {
                resend_timer.setClickable(true);
                resend_timer.setText("Resend via call");
                resend_timer.setTextColor(ContextCompat.getColor(otp.this, R.color.green));
            }
        }.start();
    }

    private void hideKeypad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
/*        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0)*/
    }
}
