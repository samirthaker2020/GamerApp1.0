package com.example.gamerapp.ui.AboutUs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.R;
import com.example.gamerapp.ui.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutUs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUs extends Fragment {

    TextView tc_condition,tc_policy,tc_eula,website;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutUs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutUs.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutUs newInstance(String param1, String param2) {
        AboutUs fragment = new AboutUs();
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
        View root = inflater.inflate(R.layout.fragment_about_us, container, false);
        ((MainPage) getActivity()).setActionBarTitle(getString(R.string.menu_aboutus));
        tc_condition=root.findViewById(R.id.terms_condition);
        tc_policy=root.findViewById(R.id.privacypolicy);
        tc_eula=root.findViewById(R.id.eula);
        website=root.findViewById(R.id.website);
        tc_condition.setText(Html.fromHtml("<a href=http://www.google.com> Terms & Conditions "));
        tc_condition.setMovementMethod(LinkMovementMethod.getInstance());
        tc_policy.setText(Html.fromHtml("<a href=http://www.google.com> Privacy Policy "));
        tc_policy.setMovementMethod(LinkMovementMethod.getInstance());
        tc_eula.setText(Html.fromHtml("<a href=http://www.google.com> End User License Agreement (EULA) "));
        tc_eula.setMovementMethod(LinkMovementMethod.getInstance());
        website.setText(Html.fromHtml("<a href=http://www.google.com> www.gamerapp.com "));
        website.setMovementMethod(LinkMovementMethod.getInstance());
        return root;

    }
}