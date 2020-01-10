package com.sgcreatives.a0003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_home extends AppCompatActivity {
Button teachers_detailes,class_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        teachers_detailes=findViewById(R.id.teachers_detailes);
        class_time=findViewById(R.id.class_time);



teachers_detailes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent j= new Intent(getApplicationContext(),Teachers_detailes_Admin.class);
        startActivity(j);
    }
});


        class_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(),Class_time_Admin.class);
                startActivity(i);
            }
        });

    }
}
