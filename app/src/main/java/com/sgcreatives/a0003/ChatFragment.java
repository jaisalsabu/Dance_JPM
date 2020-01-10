package com.sgcreatives.a0003;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
 Intent d;
 TextView name,uid,uph,uemail,udob,uaddress,uexp,udance_type;
 Button p_logout;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View root= inflater.inflate(R.layout.fragment_chat, container, false);
         uid=root.findViewById(R.id.t_name);
        uph=root.findViewById(R.id.uph);
        uemail=root.findViewById(R.id.uemail);
        udob=root.findViewById(R.id.udob);
        uaddress=root.findViewById(R.id.uaddress);
        uexp=root.findViewById(R.id.uexp);
        name=root.findViewById(R.id.dance_name);
        udance_type=root.findViewById(R.id.udance_type);
        p_logout=root.findViewById(R.id.p_logout);
        String Item = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("dance");
        String email = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("email");
        String nam = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("nam");
        String dd = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("date");
        String add = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("add");
        String ex = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("exp");
        String ty = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("dt");
        String d = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("i");
        udob.setText(dd);
        uaddress.setText(add);
        name.setText(nam);
        uph.setText(Item);
        uemail.setText(email);
        uexp.setText(ex);
        udance_type.setText(ty);
        uid.setText(d);
        return root;






    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chats, menu);
    }


}
