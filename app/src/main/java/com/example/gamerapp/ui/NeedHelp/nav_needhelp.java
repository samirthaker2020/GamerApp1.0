package com.example.gamerapp.ui.NeedHelp;

import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_needhelp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_needhelp extends Fragment {
TextView email2,customercare,nh_weblink,usermanual;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_needhelp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_needhelp.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_needhelp newInstance(String param1, String param2) {
        nav_needhelp fragment = new nav_needhelp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_nav_needhelp, container, false);
        ((MainPage) getActivity()).setActionBarTitle(getString(R.string.menu_needhelp));
        email2=root.findViewById(R.id.emailid2);
        usermanual=root.findViewById(R.id.usermanual);
        customercare=root.findViewById(R.id.customercare);
        nh_weblink=root.findViewById(R.id.nh_weblink);
        nh_weblink.setText(Html.fromHtml("<a href=http://www.google.com> www.gamerapp.com "));
        nh_weblink.setMovementMethod(LinkMovementMethod.getInstance());
        usermanual.setText(Html.fromHtml("<a href=http://www.google.com> User Manual v1.0"));
        usermanual.setMovementMethod(LinkMovementMethod.getInstance());
        email2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email2.getText().toString() });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Support/Query//"+ Constants.CURRENT_USER + "//"+String.valueOf(Constants.CUURENT_USERID));
                intent.putExtra(Intent.EXTRA_TEXT, "Dear Support Team,");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        customercare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + customercare.getText().toString()));// Initiates the Intent
                startActivity(intent);
            }
        });
        return root;
    }
}