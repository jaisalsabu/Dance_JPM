package com.sgcreatives.a0003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {
TextView signin,admin_btn;
    Button login;
EditText uph;
String a,b,c,d,e,g,h,f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signin = findViewById(R.id.signin);
        login = findViewById(R.id.login);
        uph = findViewById(R.id.uph);
        admin_btn = findViewById(R.id.admin_btn);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            if (uph.getText().toString().isEmpty()) {
                uph.setError("enter a avlid phone number");
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Dance_App_JPM/checkreg.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//If we are getting success from server
                                uph.getText().clear();

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");
                                        a=json_obj.getString("phone");
                                        b=json_obj.getString("email");
                                        c=json_obj.getString("name");
                                        d=json_obj.getString("dob");
                                        e=json_obj.getString("address");
                                        f=json_obj.getString("id");
                                        g=json_obj.getString("experience");
                                        h=json_obj.getString("dance_type");


                                    }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(Signin.this,"Registration Successful", Toast.LENGTH_LONG).show();
                                if (response.contains("success")) {
                                    Intent in = new Intent(Signin.this,MainActivity.class);
                                    in.putExtra("dance",a);
                                    in.putExtra("email",b);
                                    in.putExtra("nam",c);
                                    in.putExtra("date",d);
                                    in.putExtra("add",e);
                                    in.putExtra("exp",g);
                                    in.putExtra("dt",h);
                                    in.putExtra("i",f);
                                    startActivity(in);
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
                        params.put("phone", uph.getText().toString());

// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                        return params;
                    }

                };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(Signin.this);
                requestQueue.add(stringRequest);
            }

        }


        });





admin_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent j= new Intent(getApplicationContext(),Admin_login.class);
        startActivity(j);
    }
});

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);

            }
        });
    }


}
