package com.sgcreatives.a0003;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

View root;
TextView dance_name,t_name,d_time;
EditText d_date;
Button d_btn;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
         root= inflater.inflate(R.layout.fragment_status, container, false);
        dance_name=root.findViewById(R.id.dance_name);
        t_name=root.findViewById(R.id.t_name);
        d_time=root.findViewById(R.id.d_time);
        d_date=root.findViewById(R.id.d_date);
        d_btn=root.findViewById(R.id.d_btn);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        d_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                d_date.setText(day + "/" + (month + 0) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_status, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            SharedPreferences sharedPreferences;
            sharedPreferences= Objects.requireNonNull(getContext()).getSharedPreferences("reg",MODE_PRIVATE);
            SharedPreferences.Editor e=sharedPreferences.edit();
            e.clear();
            e.apply();
            startActivity(new Intent(getContext(),Signin.class));
        }
        return true;
    }
}
