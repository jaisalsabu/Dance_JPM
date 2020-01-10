package com.sgcreatives.a0003;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class regtwo extends AppCompatActivity implements
    AdapterView.OnItemSelectedListener {
        String[] dance = { "Bharatanatyam", "Kuchipudi", "Mohiniyattam", "Kerala Natanam", "Odisi","Flok"};
    String[] neww = { "Retraining School","New Student"};

    TextView nametxt, emailtxt, dobtxt,addresstxt, login_title;
    TextView logo;
    Spinner projectname,projectfield;
    LinearLayout already_have_account_layout;
    CardView register_card;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regtwo);
        i=getIntent();

        projectfield= findViewById(R.id.projectfield);
        nametxt = findViewById(R.id.nametxt);
        projectname = findViewById(R.id.projectname);
        emailtxt = findViewById(R.id.emailtxt);
        projectname.setOnItemSelectedListener(this);


        dobtxt = findViewById(R.id.dobtxt);
        addresstxt= findViewById(R.id.addresstxt);
//        logo = findViewById(R.id.logo);

        already_have_account_layout = findViewById(R.id.already_have_account_text);
        register_card = findViewById(R.id.register_card);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dance);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        projectname.setAdapter(aa);
//        Toast.makeText(regtwo.this,projectname.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
        ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,neww);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        projectfield.setAdapter(a);
    }


    public void registerButtonf(View view) { {

        }
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Dance_App_JPM/Registration1.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server

                            Toast.makeText(regtwo.this,response,Toast.LENGTH_LONG).show();
                            if(response.equals("Registration Successful"))
                            {
                                SharedPreferences sh=getSharedPreferences("reg",MODE_PRIVATE);
                                SharedPreferences.Editor e=sh.edit();
                                e.putBoolean("hi",true);
                                e.apply();
                                Intent in=new Intent(regtwo.this,MainActivity.class);
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
params.put("sname",i.getStringExtra("name"));
params.put("semail",i.getStringExtra("email"));
params.put("phone",i.getStringExtra("phone"));
                    params.put("sdob",i.getStringExtra("dob"));
                    params.put("saddress",i.getStringExtra("address"));
                    params.put("pname",projectfield.getSelectedItem().toString().toLowerCase());
                    params.put("project",projectname.getSelectedItem().toString().toLowerCase());

// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                    return params;
                }

            };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(regtwo.this);
            requestQueue.add(stringRequest);
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

