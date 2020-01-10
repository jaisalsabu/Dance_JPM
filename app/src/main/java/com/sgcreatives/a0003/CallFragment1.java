package com.sgcreatives.a0003;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import android.widget.Button;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment1 extends Fragment implements
        AdapterView.OnItemSelectedListener {
    String[] dance = { "Bharatanatyam", "Kuchipudi", "Mohiniyattam", "Kerala Natanam", "Odisi","Flok","Iam Not Intrested"};
EditText id;
    Button regnew_btn,online;
    Spinner dance_type;

final int RequestPermissionCode=1;
    private IntentIntegrator qrScan;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EnableRuntimePermission();

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View root= inflater.inflate(R.layout.fragment_call1, container, false);
         dance_type=root.findViewById(R.id.dance_type);
         id=root.findViewById(R.id.id);
        regnew_btn=root.findViewById(R.id.regnew_btn);
        online=root.findViewById(R.id.aa);
        String Item = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("i");
        id.setText(Item);

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,dance);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        dance_type.setAdapter(aa);
//        Toast.makeText(regtwo.this,projectname.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n=new Intent(getContext(),Online.class);
                startActivity(n);

            }
        });
        regnew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Update will happen soon!")
                        .setConfirmText("Update!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Dance_App_JPM/update_dance.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
//If we are getting success from server
                                                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                                                try {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");


                                                    }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        },

                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                                            }

                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
//Adding parameters to request
                                        params.put("newdance",dance_type.getSelectedItem().toString().toLowerCase());
                                        params.put("idno", id.getText().toString());
//                        params.put("nort",new_st.getSelectedItem().toString().toLowerCase());

//returning parameter
                                        return params;
                                    }

                                };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                requestQueue.add(stringRequest);
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            }


        });







        return root;




    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                Manifest.permission.CAMERA)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);


        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

// Toast.makeText(Cpature_image.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(),dance[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    }

